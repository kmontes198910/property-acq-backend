package com.kynsoft.wamessaging.infrastructure.client;

import com.kynsoft.wamessaging.application.dto.MessageResponse;
import com.kynsoft.wamessaging.application.dto.MessageStatusNotification;
import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP para facilitar la integración de otros servicios con el microservicio wa-messaging
 * Esta clase se puede copiar/adaptar en otros microservicios que necesiten enviar mensajes WhatsApp
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WhatsAppMessagingClient {

    private final RestTemplate restTemplate;
    
    @Value("${wa.messaging.service.url:http://localhost:8080}")
    private String serviceUrl;
    
    /**
     * Envía una solicitud de mensaje WhatsApp al servicio wa-messaging
     * 
     * @param request datos del mensaje a enviar
     * @return true si el mensaje se ha encolado correctamente
     */
    public boolean sendMessage(SendMessageRequest request) {
        try {
            String url = serviceUrl + "/api/integration/messages";
            
            ResponseEntity<MessageResponse> response = restTemplate.postForEntity(
                    url, request, MessageResponse.class);
                    
            if (response.getStatusCode() == HttpStatus.ACCEPTED) {
                log.info("Solicitud de mensaje WhatsApp encolada correctamente para: {}", 
                        request.getRecipientPhone());
                return true;
            } else {
                log.warn("Error al encolar mensaje WhatsApp. Status: {}, Mensaje: {}", 
                        response.getStatusCode(), 
                        response.getBody() != null ? response.getBody().getMessage() : "Sin detalles");
                return false;
            }
        } catch (Exception e) {
            log.error("Error al enviar solicitud de mensaje WhatsApp", e);
            return false;
        }
    }
    
    /**
     * Envía una actualización de estado de mensaje WhatsApp al servicio wa-messaging
     * 
     * @param notification datos de la notificación de estado
     * @return true si la notificación se ha encolado correctamente
     */
    public boolean updateMessageStatus(MessageStatusNotification notification) {
        try {
            String url = serviceUrl + "/api/integration/status";
            
            ResponseEntity<MessageResponse> response = restTemplate.postForEntity(
                    url, notification, MessageResponse.class);
                    
            if (response.getStatusCode() == HttpStatus.ACCEPTED) {
                log.info("Notificación de estado WhatsApp encolada correctamente para ID: {}", 
                        notification.getExternalId());
                return true;
            } else {
                log.warn("Error al encolar notificación de estado WhatsApp. Status: {}, Mensaje: {}", 
                        response.getStatusCode(), 
                        response.getBody() != null ? response.getBody().getMessage() : "Sin detalles");
                return false;
            }
        } catch (Exception e) {
            log.error("Error al enviar notificación de estado WhatsApp", e);
            return false;
        }
    }
}
