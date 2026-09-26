package com.api.cavoshbackend.local.controller;

import com.api.cavoshbackend.local.dto.response.LocalResponse;
import com.api.cavoshbackend.local.service.LocalService;
import com.api.cavoshbackend.shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locales")
public class LocalController {

    private final LocalService localService;
    private final Clock clock;

    @GetMapping
    public ApiResponse<List<LocalResponse>> listarPorDistrito(
            @RequestParam(name = "distritoId") Long distritoId
    ) {
        return new ApiResponse<>(true, "Locales obtenidos", localService.listarPorDistrito(distritoId), Instant.now(clock));
    }

    @GetMapping("/frecuentes")
    public ApiResponse<List<LocalResponse>> listarFrecuentes() {
        return new ApiResponse<>(true, "Locales frecuentes obtenidos", localService.listarFrecuentes(), Instant.now(clock));
    }

}
