package com.api.cavoshbackend.favorito.controller;

import com.api.cavoshbackend.favorito.dto.response.FavoritoResponse;
import com.api.cavoshbackend.favorito.service.FavoritoService;
import com.api.cavoshbackend.shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final Clock clock;

    @GetMapping
    public ApiResponse<List<FavoritoResponse>> listar(@AuthenticationPrincipal Jwt jwt) {
        return new ApiResponse<>(true, "Favoritos obtenidos",
                favoritoService.listar(Long.valueOf(jwt.getSubject())), Instant.now(clock));
    }

    @PutMapping("/{productoId}")
    public ApiResponse<Void> agregar(@AuthenticationPrincipal Jwt jwt, @PathVariable("productoId") Long productoId) {
        favoritoService.agregar(Long.valueOf(jwt.getSubject()), productoId);
        return new ApiResponse<>(true, "Producto agregado a favoritos", null, Instant.now(clock));
    }

    @DeleteMapping("/{productoId}")
    public ApiResponse<Void> quitar(@AuthenticationPrincipal Jwt jwt, @PathVariable("productoId") Long productoId) {
        favoritoService.quitar(Long.valueOf(jwt.getSubject()), productoId);
        return new ApiResponse<>(true, "Producto quitado de favoritos", null, Instant.now(clock));
    }
}
