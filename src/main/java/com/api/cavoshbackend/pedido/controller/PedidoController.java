package com.api.cavoshbackend.pedido.controller;

import com.api.cavoshbackend.pedido.dto.request.CrearPedidoRequest;
import com.api.cavoshbackend.pedido.dto.response.PedidoResponse;
import com.api.cavoshbackend.pedido.dto.response.PedidoResumenResponse;
import com.api.cavoshbackend.pedido.service.PedidoService;
import com.api.cavoshbackend.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final Clock clock;

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CrearPedidoRequest request
    ) {
        PedidoResponse response = pedidoService.crear(Long.valueOf(jwt.getSubject()), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Pedido creado", response, Instant.now(clock)));
    }

    @GetMapping
    public ApiResponse<List<PedidoResumenResponse>> listar(@AuthenticationPrincipal Jwt jwt) {
        return new ApiResponse<>(true, "Pedidos obtenidos",
                pedidoService.listar(Long.valueOf(jwt.getSubject())), Instant.now(clock));
    }

    @GetMapping("/{id}")
    public ApiResponse<PedidoResponse> obtener(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") Long id
    ) {
        return new ApiResponse<>(true, "Pedido obtenido",
                pedidoService.obtener(Long.valueOf(jwt.getSubject()), id), Instant.now(clock));
    }
}
