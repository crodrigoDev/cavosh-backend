package com.api.cavoshbackend.producto.controller;

import com.api.cavoshbackend.producto.dto.response.CategoriaResponse;
import com.api.cavoshbackend.producto.service.CategoriaService;
import com.api.cavoshbackend.shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final Clock clock;

    @GetMapping
    public ApiResponse<List<CategoriaResponse>> listar() {
        return new ApiResponse<>(true, "Categorías obtenidas", categoriaService.listar(), Instant.now(clock));
    }
}
