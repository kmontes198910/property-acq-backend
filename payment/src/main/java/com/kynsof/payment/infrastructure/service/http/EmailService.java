package com.kynsof.payment.infrastructure.service.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${email.service.base-url}")
    private String emailServiceBaseUrl;

    public EmailService(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    /**
     * Método para enviar un correo electrónico utilizando MailJet.
     *
     * @param recipientEmail Lista con los destinatarios del correo.
     * @param mailJetVars    Variables dinámicas a incluir en el correo.
     * @param mailJetAttachments Lista de adjuntos (puede ser vacía).
     * @param subject        Asunto del correo.
     * @param templateId     ID de la plantilla de MailJet.
     * @return `true` si el correo se envió correctamente, `false` en caso de error.
     */
    public boolean sendEmail(List<Map<String, String>> recipientEmail,
                             List<Map<String, String>> mailJetVars,
                             List<Map<String, Object>> mailJetAttachments,
                             String subject,
                             String templateId) {
        try {
            String emailServiceUrl = emailServiceBaseUrl + "/api/mail/send/email";

            String requestBodyJson = objectMapper.writeValueAsString(Map.of(
                    "recipientEmail", recipientEmail,
                    "mailJetVars", mailJetVars,
                    "mailJetAttachments", mailJetAttachments,
                    "subject", subject,
                    "templateId", templateId
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(emailServiceUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("✅ Email sent successfully: " + response.body());
                return true;
            } else {
                System.err.println("❌ Error sending email: " + response.statusCode() + " - " + response.body());
                return false;
            }

        } catch (Exception e) {
            System.err.println("❌ Exception sending email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}