package com.api.cavoshbackend.usuario.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String remitente;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}") String remitente
    ){
        this.mailSender = mailSender;
        this.remitente = remitente;
    }

    public void enviarCodigoVerificacion(
            String destinatario,
            String codigo
    ){
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setFrom(remitente);
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de Verificación - Cavosh");
        mensaje.setText("""
                Hola,
                
                Tu código de verificación es:
                
                %s
                
                Este código expirara en 10 minutos.
                
                Si no solicitaste este código, puedes ignorar este correo.
                
                """.formatted(codigo));

        mailSender.send(mensaje);
    }

}
