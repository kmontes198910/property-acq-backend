//package com.kynsoft.wamessaging.application.service;
//
//import com.kynsoft.wamessaging.application.dto.MessageResponse;
//import com.kynsoft.wamessaging.application.dto.MessageStatusNotification;
//import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
//import com.kynsoft.wamessaging.domain.entity.MessageStatus;
//import com.kynsoft.wamessaging.domain.entity.WhatsAppMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.BlockingQueue;
//
///**
// * Servicio que procesa las colas internas de mensajes y notificaciones
// */
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MessageQueueConsumerService {
//
//    private final BlockingQueue<SendMessageRequest> messageSendQueue;
//    private final BlockingQueue<MessageStatusNotification> messageStatusQueue;
//    private final MessageCoordinatorService messageCoordinatorService;
//
//    /**
//     * Procesa periódicamente las solicitudes de envío en la cola
//     */
//    @Scheduled(fixedDelayString = "${wa.messaging.queue.poll-interval:100}")
//    public void processMessageSendQueue() {
//        int processed = 0;
//
//        // Procesar hasta 10 mensajes por ejecución para evitar bloqueos largos
//        while (processed < 10 && !messageSendQueue.isEmpty()) {
//            try {
//                SendMessageRequest request = messageSendQueue.poll();
//                if (request != null) {
//                    log.info("Procesando solicitud de envío para: {}", request.getRecipientPhone());
//
//                    MessageResponse response = messageCoordinatorService.queueMessage(request);
//                    log.info("Mensaje encolado con ID: {}", response.getId());
//
//                    processed++;
//                }
//            } catch (Exception e) {
//                log.error("Error al procesar solicitud de envío desde la cola", e);
//            }
//        }
//    }
//
//    /**
//     * Procesa periódicamente las notificaciones de estado en la cola
//     */
//    @Scheduled(fixedDelayString = "${wa.messaging.queue.poll-interval:100}")
//    public void processMessageStatusQueue() {
//        int processed = 0;
//
//        // Procesar hasta 10 mensajes por ejecución para evitar bloqueos largos
//        while (processed < 10 && !messageStatusQueue.isEmpty()) {
//            try {
//                MessageStatusNotification notification = messageStatusQueue.poll();
//                if (notification != null) {
//                    log.info("Procesando notificación de estado para mensaje externo: {}, estado: {}",
//                            notification.getExternalId(), notification.getStatus());
//
//                    MessageStatus newStatus;
//                    try {
//                        newStatus = MessageStatus.valueOf(notification.getStatus().toUpperCase());
//                    } catch (IllegalArgumentException e) {
//                        log.warn("Estado de mensaje desconocido: {}, usando UNKNOWN", notification.getStatus());
//                        newStatus = MessageStatus.UNKNOWN;
//                    }
//
//                    messageCoordinatorService.updateMessageStatus(
//                            notification.getExternalId(),
//                            newStatus,
//                            notification.getErrorMessage()
//                    );
//
//                    processed++;
//                }
//            } catch (Exception e) {
//                log.error("Error al procesar notificación de estado desde la cola", e);
//            }
//        }
//    }
//
//    /**
//     * Método para encolar una solicitud de envío de mensaje (para uso por otros servicios)
//     */
//    public boolean queueSendMessageRequest(SendMessageRequest request) {
//        try {
//            boolean added = messageSendQueue.offer(request);
//            if (added) {
//                log.debug("Solicitud de envío encolada para: {}", request.getRecipientPhone());
//            } else {
//                log.warn("No se pudo encolar la solicitud de envío, cola llena");
//            }
//            return added;
//        } catch (Exception e) {
//            log.error("Error al encolar solicitud de envío", e);
//            return false;
//        }
//    }
//
//    /**
//     * Método para encolar una notificación de estado (para uso por otros servicios)
//     */
//    public boolean queueStatusNotification(MessageStatusNotification notification) {
//        try {
//            boolean added = messageStatusQueue.offer(notification);
//            if (added) {
//                log.debug("Notificación de estado encolada para: {}", notification.getExternalId());
//            } else {
//                log.warn("No se pudo encolar la notificación de estado, cola llena");
//            }
//            return added;
//        } catch (Exception e) {
//            log.error("Error al encolar notificación de estado", e);
//            return false;
//        }
//    }
//}
