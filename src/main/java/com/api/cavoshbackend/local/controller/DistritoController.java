package com.api.cavoshbackend.local.controller;

import com.api.cavoshbackend.local.dto.response.DistritoResponse;
import com.api.cavoshbackend.local.service.DistritoService;
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
@RequestMapping("/api/v1/distritos")
public class DistritoController {

    private final DistritoService distritoService;
    private final Clock clock;

    @GetMapping
    public ApiResponse<List<DistritoResponse>> listar() {
        return new ApiResponse<>(true, "Distritos obtenidos", distritoService.listar(), Instant.now(clock));
    }
}
