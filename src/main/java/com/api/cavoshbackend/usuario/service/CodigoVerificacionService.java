package com.api.cavoshbackend.usuario.service;

import com.api.cavoshbackend.usuario.enums.EstadoCodigo;
import com.api.cavoshbackend.usuario.exception.CodigoVerificacionInvalidoException;
import com.api.cavoshbackend.usuario.model.CodigoVerificacion;
import com.api.cavoshbackend.usuario.model.Usuario;
import com.api.cavoshbackend.usuario.repository.CodigoVerificacionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class CodigoVerificacionService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Duration DURACION = Duration.ofMinutes(10);

    private final CodigoVerificacionRepository codigoRepository;
    private final EmailService emailService;
    private final Clock clock;

    @Transactional
    public void crearYEnviar(Usuario usuario) {
        expirarCodigosAnteriores(usuario);

        String codigo = generarCodigo();
        Instant expiracion = Instant.now(clock).plus(DURACION);

        CodigoVerificacion codigoVerificacion = new CodigoVerificacion(
                codigo,
                expiracion,
                usuario
        );

        codigoRepository.save(codigoVerificacion);

        emailService.enviarCodigoVerificacion(usuario.getEmail(), codigo);
    }

    @Transactional
    public void verificar(Usuario usuario, String codigoRecibido) {
        CodigoVerificacion codigo = codigoRepository
                .findFirstByUsuarioAndEstadoOrderByFechaCreacionDesc(usuario, EstadoCodigo.VIGENTE)
                .orElseThrow(CodigoVerificacionInvalidoException::new);

        Instant now = Instant.now(clock);

        if (codigo.estaExpirado(now))
            throw new CodigoVerificacionInvalidoException();


        if (!codigo.coincideCon(codigoRecibido.strip()))
            throw new CodigoVerificacionInvalidoException();

        codigo.marcarUsado();
    }

    private String generarCodigo() {
        int numero = SECURE_RANDOM.nextInt(1_000_000);
        return String.format(Locale.ROOT, "%06d", numero);
    }

    private void expirarCodigosAnteriores(Usuario usuario){
        codigoRepository
                .findAllByUsuarioAndEstado(usuario, EstadoCodigo.VIGENTE)
                .forEach(CodigoVerificacion::marcarExpirado);
    }
}
