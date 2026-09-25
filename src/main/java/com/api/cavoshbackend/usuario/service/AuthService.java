package com.api.cavoshbackend.usuario.service;

import com.api.cavoshbackend.usuario.dto.request.LoginRequest;
import com.api.cavoshbackend.usuario.dto.request.GoogleLoginRequest;
import com.api.cavoshbackend.usuario.dto.request.RegistrarRequest;
import com.api.cavoshbackend.usuario.dto.request.VerificarCodigoRequest;
import com.api.cavoshbackend.usuario.dto.response.LoginResponse;
import com.api.cavoshbackend.usuario.exception.CodigoVerificacionInvalidoException;
import com.api.cavoshbackend.usuario.exception.CuentaNoVerificadaException;
import com.api.cavoshbackend.usuario.exception.EmailYaEstaRegistradoException;
import com.api.cavoshbackend.usuario.model.Usuario;
import com.api.cavoshbackend.usuario.model.CuentaSocial;
import com.api.cavoshbackend.usuario.enums.ProveedorSocial;
import com.api.cavoshbackend.usuario.repository.CuentaSocialRepository;
import com.api.cavoshbackend.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final CodigoVerificacionService codigoVerificacionService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CuentaSocialRepository cuentaSocialRepository;
    private final GoogleIdTokenService googleIdTokenService;

    public LoginResponse login(LoginRequest request) {
        String emailNormalizado = request.email().strip().toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository.findByEmail(emailNormalizado)
                .orElseThrow(() ->
                            new BadCredentialsException("Credenciales Inválidas")
                        );

        if(!passwordEncoder.matches(
                request.password(),
                usuario.getPasswordHash()
        ))
            throw new BadCredentialsException("Credenciales Inválidas");

        if(!usuario.isActivo()){
            codigoVerificacionService.crearYEnviar(usuario);
            throw new CuentaNoVerificadaException();
        }

        return jwtService.generarToken(usuario);
    }

    @Transactional
    public LoginResponse loginGoogle(GoogleLoginRequest request) {
        Jwt googleToken = googleIdTokenService.validar(request.idToken());
        String googleId = googleToken.getSubject();

        Usuario usuario = cuentaSocialRepository
                .findByProveedorAndIdProveedor(ProveedorSocial.GOOGLE, googleId)
                .map(CuentaSocial::getUsuario)
                .orElseGet(() -> crearOVincularUsuarioGoogle(googleToken, googleId));

        usuario.activar();
        return jwtService.generarToken(usuario);
    }

    private Usuario crearOVincularUsuarioGoogle(Jwt googleToken, String googleId) {
        String email = googleToken.getClaimAsString("email")
                .strip()
                .toLowerCase(Locale.ROOT);
        String nombre = googleToken.getClaimAsString("name");
        String foto = googleToken.getClaimAsString("picture");

        if (nombre == null || nombre.isBlank()) {
            nombre = email.substring(0, email.indexOf('@'));
        }
        String nombreUsuario = nombre;

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario(nombreUsuario, email, null, foto);
                    nuevoUsuario.activar();
                    return usuarioRepository.save(nuevoUsuario);
                });

        if (!usuario.isActivo()) {
            usuario.activar();
        }

        cuentaSocialRepository.save(new CuentaSocial(
                googleId,
                ProveedorSocial.GOOGLE,
                usuario
        ));

        return usuario;
    }


    @Transactional
    public String registrar(RegistrarRequest request) {
        Objects.requireNonNull(
                request,
                "El request no puede ser null"
        );

        String emailNormalizado = request.email().strip().toLowerCase(Locale.ROOT);

        if(usuarioRepository.existsByEmail(emailNormalizado))
            throw new EmailYaEstaRegistradoException();

        String passwordHash = passwordEncoder.encode(request.password());

        Usuario usuario = new Usuario(
                request.nombreCompleto(),
                emailNormalizado,
                passwordHash,
                null
        );

        usuarioRepository.save(usuario);
        codigoVerificacionService.crearYEnviar(usuario);

        return usuario.getEmail();
    }

    @Transactional
    public String verificarCodigo(VerificarCodigoRequest request) {
        String emailNormalizado = request.email().strip().toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository.findByEmail(emailNormalizado)
                .orElseThrow(CodigoVerificacionInvalidoException::new);

        if(usuario.isActivo())
            return usuario.getEmail();

        codigoVerificacionService.verificar(usuario, request.codigo());

        usuario.activar();

        return usuario.getEmail();
    }
}
