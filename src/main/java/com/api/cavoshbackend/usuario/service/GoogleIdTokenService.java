package com.api.cavoshbackend.usuario.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
public class GoogleIdTokenService {

    private final JwtDecoder jwtDecoder;

    public GoogleIdTokenService(
            @Qualifier("googleJwtDecoder") JwtDecoder jwtDecoder
    ) {
        this.jwtDecoder = jwtDecoder;
    }

    public Jwt validar(String idToken) {
        try {
            Jwt jwt = jwtDecoder.decode(idToken);

            if (!Boolean.TRUE.equals(jwt.getClaimAsBoolean("email_verified"))
                    || jwt.getSubject() == null
                    || jwt.getSubject().isBlank()
                    || jwt.getClaimAsString("email") == null
                    || jwt.getClaimAsString("email").isBlank()) {
                throw new BadCredentialsException("Token de Google inválido");
            }

            return jwt;
        } catch (JwtException exception) {
            throw new BadCredentialsException("Token de Google inválido", exception);
        }
    }
}
