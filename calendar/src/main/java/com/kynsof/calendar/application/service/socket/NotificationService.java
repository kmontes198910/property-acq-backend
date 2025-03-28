package com.kynsof.calendar.application.service.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NotificationService {

    @Value("${notification.host:localhost}")
    private String host;

    @Value("${notification.port:7171}")
    private String port;

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(Object message, String endpoint) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var request = new HttpEntity<>(message, headers);

            String notificationUrl = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host(host)
                    .port(port)
                    .path(endpoint)
                    .toUriString();

            ResponseEntity<Boolean> response = restTemplate.postForEntity(notificationUrl, request, Boolean.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Notificación enviada exitosamente.");
            } else {
                System.err.println("Error al enviar la notificación: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
        }
    }
}
