package com.api.cavoshbackend.producto.controller;

import com.api.cavoshbackend.producto.dto.response.ProductoDetalleResponse;
import com.api.cavoshbackend.producto.dto.response.ProductoResumenResponse;
import com.api.cavoshbackend.producto.service.ProductoService;
import com.api.cavoshbackend.shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final Clock clock;

    @GetMapping
    public ApiResponse<List<ProductoResumenResponse>> listar(
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "nuevo", required = false) Boolean nuevo,
            @RequestParam(name = "frecuente", required = false) Boolean frecuente,
            @RequestParam(name = "q", required = false) String q
    ) {
        return new ApiResponse<>(true, "Productos obtenidos",
                productoService.listar(categoriaId, nuevo, frecuente, q), Instant.now(clock));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductoDetalleResponse> obtener(@PathVariable("id") Long id) {
        return new ApiResponse<>(true, "Producto obtenido", productoService.obtener(id), Instant.now(clock));
    }
}
