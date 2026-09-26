package com.api.cavoshbackend.favorito.mapper;

import com.api.cavoshbackend.favorito.dto.response.FavoritoResponse;
import com.api.cavoshbackend.favorito.model.Favorito;
import com.api.cavoshbackend.producto.model.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FavoritoMapper {

    public FavoritoResponse toResponse(Favorito favorito, BigDecimal precio) {
        Producto producto = favorito.getProducto();
        return new FavoritoResponse(
                favorito.getId(), producto.getId(), producto.getNombre(), producto.getImagenUrl(),
                precio, producto.isDisponible(), favorito.getFechaCreacion());
    }
}
