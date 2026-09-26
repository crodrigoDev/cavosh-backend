package com.api.cavoshbackend.producto.service;

import com.api.cavoshbackend.producto.dto.response.OpcionPersonalizacionResponse;
import com.api.cavoshbackend.producto.dto.response.ProductoDetalleResponse;
import com.api.cavoshbackend.producto.dto.response.ProductoResumenResponse;
import com.api.cavoshbackend.producto.dto.response.ProductoSizeResponse;
import com.api.cavoshbackend.producto.exception.ProductoNoEncontradoException;
import com.api.cavoshbackend.producto.model.Producto;
import com.api.cavoshbackend.producto.model.ProductoSize;
import com.api.cavoshbackend.producto.repository.OpcionPersonalizacionRepository;
import com.api.cavoshbackend.producto.repository.ProductoRepository;
import com.api.cavoshbackend.producto.repository.ProductoSizeRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoSizeRepository productoSizeRepository;
    private final OpcionPersonalizacionRepository opcionRepository;

    @Transactional(readOnly = true)
    public List<ProductoResumenResponse> listar(Long categoriaId, Boolean nuevo, Boolean frecuente, String q) {
        String busqueda = q == null ? "" : q.strip().toLowerCase(Locale.ROOT);

        List<Producto> productos = productoRepository.findAll((root, query, cb) -> {
            List<Predicate> filtros = new ArrayList<>();
            filtros.add(cb.isTrue(root.get("disponible")));
            if (categoriaId != null) {
                filtros.add(cb.equal(root.get("categoria").get("id"), categoriaId));
            }
            if (nuevo != null) {
                filtros.add(cb.equal(root.get("nuevo"), nuevo));
            }
            if (frecuente != null) {
                filtros.add(cb.equal(root.get("frecuente"), frecuente));
            }
            if (!busqueda.isBlank()) {
                filtros.add(cb.like(cb.lower(root.get("nombre")), "%" + busqueda + "%"));
            }
            return cb.and(filtros.toArray(Predicate[]::new));
        }, Sort.by("id"));

        if (productos.isEmpty()) {
            return List.of();
        }

        List<Long> ids = productos.stream().map(Producto::getId).toList();
        Map<Long, BigDecimal> precios = productoSizeRepository.findByProductoIdInAndPredeterminadoTrue(ids)
                .stream()
                .collect(Collectors.toMap(
                        size -> size.getProducto().getId(),
                        ProductoSize::getPrecio));

        return productos.stream()
                .map(producto -> new ProductoResumenResponse(
                        producto.getId(),
                        producto.getCategoria().getId(),
                        producto.getNombre(),
                        producto.getImagenUrl(),
                        producto.isNuevo(),
                        producto.isFrecuente(),
                        precios.get(producto.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoDetalleResponse obtener(Long id) {
        Producto producto = productoRepository.findByIdAndDisponibleTrue(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        List<ProductoSizeResponse> tamanos = productoSizeRepository.findByProductoIdOrderByIdAsc(id)
                .stream()
                .map(size -> new ProductoSizeResponse(
                        size.getId(), size.getNombre(), size.getPrecio(), size.isPredeterminado()))
                .toList();

        List<OpcionPersonalizacionResponse> opciones = producto.isPersonalizable()
                ? opcionRepository.findByActivoTrueOrderByTipoAscIdAsc()
                .stream()
                .map(opcion -> new OpcionPersonalizacionResponse(
                        opcion.getId(), opcion.getTipo(), opcion.getNombre(),
                        opcion.getPrecioExtra(), opcion.isPredeterminada()))
                .toList()
                : List.of();

        return new ProductoDetalleResponse(
                producto.getId(),
                producto.getCategoria().getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getImagenUrl(),
                producto.isNuevo(),
                producto.isFrecuente(),
                producto.isPersonalizable(),
                tamanos,
                opciones);
    }
}
