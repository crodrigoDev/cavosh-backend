package com.api.cavoshbackend.usuario.service;

import com.api.cavoshbackend.usuario.dto.response.LoginResponse;
import com.api.cavoshbackend.usuario.dto.response.UsuarioResponse;
import com.api.cavoshbackend.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class JwtService {

    private static final Duration DURACION = Duration.ofHours(1);

    private final JwtEncoder jwtEncoder;
    private final Clock clock;

    @Value("${spring.app.jwt.issuer}")
    private String issuer;

    public LoginResponse generarToken(Usuario usuario) {
        Instant now = Instant.now(clock);
        Instant expiracion = Instant.now(clock).plus(DURACION);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expiracion)
                .subject(usuario.getId().toString())
                .claim("email", usuario.getEmail())
                .claim("scope", "usuario")
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();

        return new LoginResponse(
                token,
                "Bearer",
                DURACION.toSeconds(),
                new UsuarioResponse(
                        usuario.getId().toString(),
                        usuario.getNombreCompleto(),
                        usuario.getEmail(),
                        usuario.getFotoUrl()
                )
        );
    }
}
