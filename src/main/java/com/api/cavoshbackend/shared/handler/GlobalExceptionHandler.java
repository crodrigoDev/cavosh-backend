package com.api.cavoshbackend.shared.handler;

import com.api.cavoshbackend.producto.exception.ProductoNoEncontradoException;
import com.api.cavoshbackend.shared.dto.ErrorResponse;
import com.api.cavoshbackend.shared.dto.FieldErrorResponse;
import com.api.cavoshbackend.usuario.exception.CodigoVerificacionInvalidoException;
import com.api.cavoshbackend.usuario.exception.CuentaNoVerificadaException;
import com.api.cavoshbackend.usuario.exception.EmailNoEnviadoException;
import com.api.cavoshbackend.usuario.exception.EmailYaEstaRegistradoException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Clock clock;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<FieldErrorResponse> fieldErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Hay campos inválidos",
                fieldErrors,
                request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_JSON",
                "El cuerpo de la petición no es válido",
                List.of(),
                request
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS",
                "Email o contraseña incorrectos",
                List.of(),
                request
        );
    }

    @ExceptionHandler(CuentaNoVerificadaException.class)
    public ResponseEntity<ErrorResponse> handleCuentaNoVerificada(
            CuentaNoVerificadaException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "ACCOUNT_NOT_VERIFIED",
                exception.getMessage(),
                List.of(),
                request
        );
    }

    @ExceptionHandler(EmailYaEstaRegistradoException.class)
    public ResponseEntity<ErrorResponse> handleEmailRegistrado(
            EmailYaEstaRegistradoException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "EMAIL_ALREADY_REGISTERED",
                exception.getMessage(),
                List.of(),
                request
        );
    }

    @ExceptionHandler(CodigoVerificacionInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleCodigoInvalido(
            CodigoVerificacionInvalidoException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_VERIFICATION_CODE",
                exception.getMessage(),
                List.of(),
                request
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataConflict(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "DATA_CONFLICT",
                "La operación entra en conflicto con datos existentes",
                List.of(),
                request
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "MISSING_PARAMETER",
                "El parámetro " + exception.getParameterName() + " es obligatorio",
                List.of(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParameter(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_PARAMETER",
                "El parámetro " + exception.getName() + " no es válido",
                List.of(),
                request
        );
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleProductoNoEncontrado(
            ProductoNoEncontradoException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "PRODUCT_NOT_FOUND",
                exception.getMessage(),
                List.of(),
                request
        );
    }

    @ExceptionHandler(EmailNoEnviadoException.class)
    public ResponseEntity<ErrorResponse> handleEmailError(
            EmailNoEnviadoException exception,
            HttpServletRequest request
    ) {
        log.error("No se pudo enviar el correo", exception);

        return buildResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "EMAIL_SERVICE_UNAVAILABLE",
                "No se pudo enviar el correo. Inténtalo nuevamente",
                List.of(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error("Error inesperado", exception);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Ocurrió un error inesperado",
                List.of(),
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus httpStatus,
            String code,
            String message,
            List<FieldErrorResponse> fieldErrors,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                false,
                code,
                message,
                request.getRequestURI(),
                fieldErrors,
                Instant.now(clock)
        );

        return ResponseEntity.status(httpStatus).body(response);
    }
}
