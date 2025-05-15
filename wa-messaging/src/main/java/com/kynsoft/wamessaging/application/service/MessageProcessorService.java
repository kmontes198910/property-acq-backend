package com.kynsoft.wamessaging.application.service;

import com.kynsoft.wamessaging.application.dto.MessageStatusNotification;
import com.kynsoft.wamessaging.domain.entity.WhatsAppMessage;
import com.kynsoft.wamessaging.domain.service.WhatsAppMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Servicio para el procesamiento concurrente de mensajes pendientes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProcessorService {
    
    private final WhatsAppMessageService messageService;
    private final MessageCoordinatorService coordinatorService;
    private final MessageQueueConsumerService queueConsumerService;
    
    @Value("${wa.messaging.retry.max-attempts:3}")
    private int maxRetryAttempts;
    
    /**
     * Procesa periódicamente los mensajes pendientes en la base de datos
     */
    @Scheduled(fixedDelayString = "${wa.messaging.scheduler.delay:10000}")
    public void processScheduledMessages() {
        try {
            log.debug("Iniciando procesamiento programado de mensajes pendientes");
            List<WhatsAppMessage> pendingMessages = messageService.findPendingMessages(maxRetryAttempts);
            
            if (!pendingMessages.isEmpty()) {
                log.info("Procesando {} mensajes pendientes", pendingMessages.size());
                
                for (WhatsAppMessage message : pendingMessages) {
                    processMessageAsync(message);
                }
            }
        } catch (Exception e) {
            log.error("Error durante el procesamiento programado de mensajes", e);
        }
    }
    
    /**
     * Procesa un mensaje de forma asíncrona
     */
    @Async("messageProcessorExecutor")
    public CompletableFuture<Void> processMessageAsync(WhatsAppMessage message) {
        try {
            log.info("Procesando mensaje asíncrono ID: {}, destinatario: {}", 
                     message.getId(), message.getRecipientPhone());
            
            coordinatorService.sendMessage(message.getId());
            
            // Notificar el cambio de estado a través de la cola interna
            sendStatusNotification(message);
            
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("Error al procesar mensaje asíncrono", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    /**
     * Procesa un mensaje por su ID de forma asíncrona
     */
    @Async("messageProcessorExecutor")
    public CompletableFuture<Void> processMessageAsync(java.util.UUID messageId) {
        log.info("Procesando mensaje {} en hilo {}", messageId, Thread.currentThread().getName());
        
        try {
            // Obtener el mensaje
            return messageService.findById(messageId)
                .map(this::processMessageAsync)
                .orElseGet(() -> {
                    log.error("No se encontró el mensaje con ID: {}", messageId);
                    return CompletableFuture.completedFuture(null);
                });
        } catch (Exception e) {
            log.error("Error al procesar mensaje {}", messageId, e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    /**
     * Envía una notificación sobre el estado del mensaje a través de la cola interna
     */
    private void sendStatusNotification(WhatsAppMessage message) {
        try {
            MessageStatusNotification notification = MessageStatusNotification.builder()
                    .messageId(message.getId())
                    .externalId(message.getExternalId())
                    .status(message.getStatus().name())
                    .timestamp(message.getUpdatedAt() != null 
                              ? message.getUpdatedAt().toString() 
                              : java.time.LocalDateTime.now().toString())
                    .errorMessage(message.getErrorMessage())
                    .build();
            
            queueConsumerService.queueStatusNotification(notification);
            log.debug("Notificación de estado enviada para mensaje {}", message.getId());
        } catch (Exception e) {
            log.error("Error al enviar notificación de estado", e);
        }
    }
}
