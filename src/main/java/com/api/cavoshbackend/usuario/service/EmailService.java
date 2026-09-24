package com.api.cavoshbackend.usuario.service;

import com.api.cavoshbackend.usuario.exception.EmailNoEnviadoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class EmailService {

    private final RestClient resendRestClient;
    private final String remitente;

    public EmailService(
            RestClient resendRestClient,
            @Value("${spring.resend.from}") String remitente
    ){
        this.resendRestClient = resendRestClient;
        this.remitente = remitente;
    }

    public void enviarCodigoVerificacion(
            String destinatario,
            String codigo
    ){
        ResendEmailRequest request = new ResendEmailRequest(
                remitente,
                List.of(destinatario),
                "Código de verificación - Cavosh",
                """
                Hola,

                Tu código de verificación es:

                %s

                Este código expirará en 10 minutos.

                Si no solicitaste este código, puedes ignorar este correo.
                """.formatted(codigo)
        );

        try {
            ResendEmailResponse response = resendRestClient
                    .post()
                    .uri("/emails")
                    .body(request)
                    .retrieve()
                    .body(ResendEmailResponse.class);

            if(response == null || response.id() == null)
                throw new EmailNoEnviadoException();
        } catch (RestClientException exception){
            throw new EmailNoEnviadoException(exception);
        }
    }

    private record ResendEmailRequest(
            String from,
            List<String> to,
            String subject,
            String text
    ) {}

    private record ResendEmailResponse(
            String id
    ) {}

}
