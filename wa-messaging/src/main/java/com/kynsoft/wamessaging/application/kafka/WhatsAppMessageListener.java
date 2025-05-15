package com.kynsoft.wamessaging.application.kafka;

import com.kynsoft.wamessaging.application.dto.MessageStatusNotification;
import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import com.kynsoft.wamessaging.domain.entity.WhatsAppMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listener para mensajes y notificaciones de estado en Kafka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WhatsAppMessageListener {

    private final MessageCoordinatorService messageCoordinatorService;
    
    /**
     * Escucha eventos para enviar mensajes de WhatsApp
     * 
     * @param request Solicitud de envío de mensaje
     */
    @KafkaListener(topics = "${kafka.topic.message-send}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenMessageSendEvents(SendMessageRequest request) {
        log.info("Recibido evento para enviar mensaje a: {}", request.getRecipientPhone());
        
        try {
            WhatsAppMessage message = messageCoordinatorService.queueMessage(request);
            log.info("Mensaje encolado con ID: {}", message.getId());
        } catch (Exception e) {
            log.error("Error al procesar evento de envío de mensaje", e);
        }
    }
    
    /**
     * Escucha actualizaciones de estado de mensajes
     * 
     * @param notification Notificación de cambio de estado
     */
    @KafkaListener(topics = "${kafka.topic.message-status}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenMessageStatusEvents(MessageStatusNotification notification) {
        log.info("Recibida actualización de estado para mensaje externo: {}, nuevo estado: {}", 
                notification.getExternalId(), notification.getStatus());
        
        try {
            MessageStatus newStatus;
            try {
                newStatus = MessageStatus.valueOf(notification.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Estado de mensaje desconocido: {}, usando UNKNOWN", notification.getStatus());
                newStatus = MessageStatus.UNKNOWN;
            }
            
            messageCoordinatorService.updateMessageStatus(
                    notification.getExternalId(), 
                    newStatus, 
                    notification.getErrorMessage()
            );
        } catch (Exception e) {
            log.error("Error al procesar actualización de estado de mensaje", e);
        }
    }
}
