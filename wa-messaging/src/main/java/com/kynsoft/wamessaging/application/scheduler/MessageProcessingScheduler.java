package com.kynsoft.wamessaging.application.scheduler;

import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler para procesar mensajes de WhatsApp pendientes de forma periódica
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProcessingScheduler {

    private final MessageCoordinatorService messageCoordinatorService;
    
    @Value("${wa.messaging.batch.size:20}")
    private int batchSize;
    
    /**
     * Procesa mensajes pendientes cada 10 segundos
     */
    @Scheduled(fixedDelayString = "${wa.messaging.scheduler.delay:10000}")
    public void processPendingMessages() {
        log.debug("Iniciando procesamiento programado de mensajes pendientes");
        
        try {
            java.util.List<com.kynsoft.wamessaging.application.request.MessageResponse> processedMessages =
                messageCoordinatorService.processNextBatch(batchSize);
            
            if (!processedMessages.isEmpty()) {
                log.info("Procesados {} mensajes en la ejecución programada", processedMessages.size());
            } else {
                log.debug("No se encontraron mensajes pendientes para procesar");
            }
        } catch (Exception e) {
            log.error("Error durante el procesamiento programado de mensajes", e);
        }
    }
}
