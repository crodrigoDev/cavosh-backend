package com.api.cavoshbackend.pedido.service;

import com.api.cavoshbackend.local.model.Local;
import com.api.cavoshbackend.local.repository.LocalRepository;
import com.api.cavoshbackend.pedido.dto.ItemValidado;
import com.api.cavoshbackend.pedido.dto.request.CrearPedidoRequest;
import com.api.cavoshbackend.pedido.dto.request.ItemPedidoRequest;
import com.api.cavoshbackend.pedido.dto.response.PedidoResponse;
import com.api.cavoshbackend.pedido.dto.response.PedidoResumenResponse;
import com.api.cavoshbackend.pedido.exception.PedidoInvalidoException;
import com.api.cavoshbackend.pedido.exception.PedidoNoEncontradoException;
import com.api.cavoshbackend.pedido.mapper.PedidoMapper;
import com.api.cavoshbackend.pedido.model.Pedido;
import com.api.cavoshbackend.pedido.model.PedidoDetalle;
import com.api.cavoshbackend.pedido.repository.PedidoDetalleRepository;
import com.api.cavoshbackend.pedido.repository.PedidoRepository;
import com.api.cavoshbackend.producto.enums.TipoPersonalizacion;
import com.api.cavoshbackend.producto.model.OpcionPersonalizacion;
import com.api.cavoshbackend.producto.model.Producto;
import com.api.cavoshbackend.producto.model.ProductoSize;
import com.api.cavoshbackend.producto.repository.OpcionPersonalizacionRepository;
import com.api.cavoshbackend.producto.repository.ProductoRepository;
import com.api.cavoshbackend.producto.repository.ProductoSizeRepository;
import com.api.cavoshbackend.usuario.model.Usuario;
import com.api.cavoshbackend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDetalleRepository detalleRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;
    private final ProductoRepository productoRepository;
    private final ProductoSizeRepository sizeRepository;
    private final OpcionPersonalizacionRepository opcionRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional
    public PedidoResponse crear(Long usuarioId, CrearPedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .filter(Usuario::isActivo)
                .orElseThrow(() -> new PedidoInvalidoException("El usuario no está disponible"));
        Local local = localRepository.findById(request.localId())
                .filter(Local::isActivo)
                .orElseThrow(() -> new PedidoInvalidoException("El local no existe o está inactivo"));

        List<ItemValidado> items = request.items().stream().map(this::validarItem).toList();
        BigDecimal total = items.stream()
                .map(item -> item.precioUnitario().multiply(BigDecimal.valueOf(item.request().cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(new BigDecimal("99999999.99")) > 0) {
            throw new PedidoInvalidoException("El total del pedido supera el importe permitido");
        }

        Pedido pedido = pedidoRepository.save(new Pedido(
                usuario, local, request.fechaRecojo(), request.metodoPago(), total));
        List<PedidoDetalle> detalles = items.stream()
                .map(item -> pedidoMapper.toDetalle(pedido, item))
                .toList();
        detalles = detalleRepository.saveAll(detalles);
        return pedidoMapper.toResponse(pedido, detalles);
    }

    @Transactional(readOnly = true)
    public List<PedidoResumenResponse> listar(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaCreacionDescIdDesc(usuarioId)
                .stream()
                .map(pedidoMapper::toResumenResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse obtener(Long usuarioId, Long id) {
        Pedido pedido = pedidoRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
        return pedidoMapper.toResponse(pedido, detalleRepository.findByPedidoIdOrderByIdAsc(id));
    }

    private ItemValidado validarItem(ItemPedidoRequest item) {
        Producto producto = productoRepository.findByIdAndDisponibleTrue(item.productoId())
                .orElseThrow(() -> new PedidoInvalidoException("Producto no disponible: " + item.productoId()));
        ProductoSize size = sizeRepository.findById(item.productoSizeId())
                .filter(valor -> valor.getProducto().getId().equals(producto.getId()))
                .orElseThrow(() -> new PedidoInvalidoException("El size no pertenece al producto"));

        OpcionPersonalizacion milk = null;
        OpcionPersonalizacion cream = null;
        OpcionPersonalizacion caffeine = null;
        BigDecimal precio = size.getPrecio();
        if (producto.isPersonalizable()) {
            milk = validarOpcion(item.milkId(), TipoPersonalizacion.MILK);
            cream = validarOpcion(item.creamId(), TipoPersonalizacion.CREAM);
            caffeine = validarOpcion(item.caffeineId(), TipoPersonalizacion.CAFFEINE);
            precio = precio.add(milk.getPrecioExtra()).add(cream.getPrecioExtra()).add(caffeine.getPrecioExtra());
        } else if (item.milkId() != null || item.creamId() != null || item.caffeineId() != null) {
            throw new PedidoInvalidoException("El producto no admite personalización");
        }
        return new ItemValidado(item, producto, size, milk, cream, caffeine, precio);
    }

    private OpcionPersonalizacion validarOpcion(Long id, TipoPersonalizacion tipo) {
        if (id == null) {
            throw new PedidoInvalidoException("Selecciona una opción de " + tipo);
        }
        return opcionRepository.findById(id)
                .filter(opcion -> opcion.isActivo() && opcion.getTipo() == tipo)
                .orElseThrow(() -> new PedidoInvalidoException("Opción no válida para " + tipo + ": " + id));
    }

}
