package com.api.cavoshbackend.pedido.mapper;

import com.api.cavoshbackend.local.dto.response.LocalResponse;
import com.api.cavoshbackend.local.model.Local;
import com.api.cavoshbackend.pedido.dto.ItemValidado;
import com.api.cavoshbackend.pedido.dto.response.ItemPedidoResponse;
import com.api.cavoshbackend.pedido.dto.response.OpcionPedidoResponse;
import com.api.cavoshbackend.pedido.dto.response.PedidoResponse;
import com.api.cavoshbackend.pedido.dto.response.PedidoResumenResponse;
import com.api.cavoshbackend.pedido.model.Pedido;
import com.api.cavoshbackend.pedido.model.PedidoDetalle;
import com.api.cavoshbackend.producto.model.OpcionPersonalizacion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoDetalle toDetalle(Pedido pedido, ItemValidado item) {
        return new PedidoDetalle(
                pedido, item.producto(), item.size(), item.milk(), item.cream(), item.caffeine(),
                item.request().cantidad(), item.precioUnitario());
    }

    public PedidoResumenResponse toResumenResponse(Pedido pedido) {
        return new PedidoResumenResponse(
                pedido.getId(), pedido.getLocal().getId(), pedido.getLocal().getNombre(),
                pedido.getFechaCreacion(), pedido.getFechaRecojo(), pedido.getEstado(),
                pedido.getMetodoPago(), pedido.getTotal());
    }

    public PedidoResponse toResponse(Pedido pedido, List<PedidoDetalle> detalles) {
        Local local = pedido.getLocal();
        LocalResponse localResponse = new LocalResponse(
                local.getId(), local.getNombre(), local.getDireccion(), local.getHorario(), local.isFrecuente(),
                local.getDistrito().getId(), local.getDistrito().getNombre());
        List<ItemPedidoResponse> items = detalles.stream().map(this::toItemResponse).toList();
        return new PedidoResponse(
                pedido.getId(), localResponse, pedido.getFechaCreacion(), pedido.getFechaRecojo(),
                pedido.getEstado(), pedido.getMetodoPago(), pedido.getTotal(), items);
    }

    private ItemPedidoResponse toItemResponse(PedidoDetalle detalle) {
        return new ItemPedidoResponse(
                detalle.getId(), detalle.getProducto().getId(), detalle.getNombreProducto(),
                detalle.getProductoSize().getId(), detalle.getProductoSize().getNombre(),
                toOpcionResponse(detalle.getMilk()), toOpcionResponse(detalle.getCream()),
                toOpcionResponse(detalle.getCaffeine()), detalle.getCantidad(),
                detalle.getPrecioUnitario(), detalle.getTotal());
    }

    private OpcionPedidoResponse toOpcionResponse(OpcionPersonalizacion opcion) {
        return opcion == null ? null : new OpcionPedidoResponse(opcion.getId(), opcion.getNombre());
    }
}
