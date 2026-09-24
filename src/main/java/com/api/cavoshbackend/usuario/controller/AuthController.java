package com.api.cavoshbackend.usuario.controller;

import com.api.cavoshbackend.shared.dto.ApiResponse;
import com.api.cavoshbackend.usuario.dto.request.LoginRequest;
import com.api.cavoshbackend.usuario.dto.request.RegistrarRequest;
import com.api.cavoshbackend.usuario.dto.request.VerificarCodigoRequest;
import com.api.cavoshbackend.usuario.dto.response.LoginResponse;
import com.api.cavoshbackend.usuario.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final Clock clock;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ){
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity.ok(new ApiResponse<LoginResponse>(
                true,
                "Se inicio sesión exitosamente",
                loginResponse,
                Instant.now(clock)
        ));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<String>> registrar(
            @Valid @RequestBody RegistrarRequest request
    ){
        String email = authService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<String>(
                        true,
                        "Usuario registrado correctamente",
                        email,
                        Instant.now(clock)
                )
        );
    }

    @PostMapping("/verificar")
    public ResponseEntity<ApiResponse<String>> verificar(
            @Valid @RequestBody VerificarCodigoRequest request
    ){
        String email = authService.verificarCodigo(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<String>(
                        true,
                        "Usuario verificado correctamente",
                        email,
                        Instant.now(clock)
                )
        );
    }
}
