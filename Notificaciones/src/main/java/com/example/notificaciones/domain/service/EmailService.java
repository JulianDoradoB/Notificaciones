package com.example.notificaciones.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico simple.
     * @param to Destinatario del correo.
     * @param subject Asunto del correo.
     * @param text Contenido del correo.
     * @return true si el correo fue enviado exitosamente, false en caso contrario.
     */
    public boolean enviarCorreo(String to, String subject, String text) { // <-- ¡CAMBIADO a enviarCorreo!
        if (to == null || to.trim().isEmpty()) {
            log.warn("Intento de enviar correo a un destinatario nulo o vacío. Asunto: {}", subject);
            return false;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("tu.correo@gmail.com"); // <-- ¡IMPORTANTE! Reemplaza con tu remitente
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.info("📧 Correo enviado exitosamente a '{}' con asunto: '{}'", to, subject);
            return true;
        } catch (MailException e) {
            log.error("❌ Error al enviar correo a '{}' con asunto '{}': {}", to, subject, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("❌ Error inesperado al enviar correo a '{}' con asunto '{}': {}", to, subject, e.getMessage(), e);
            return false;
        }
    }
}