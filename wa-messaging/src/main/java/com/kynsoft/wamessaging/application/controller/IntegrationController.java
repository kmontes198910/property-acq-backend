//package com.kynsoft.wamessaging.application.controller;
//
//import com.kynsoft.wamessaging.application.dto.MessageResponse;
//import com.kynsoft.wamessaging.application.dto.MessageStatusNotification;
//import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
//import com.kynsoft.wamessaging.application.service.MessageQueueConsumerService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//
///**
// * Controlador para la integración con otros microservicios
// * Esta API proporciona endpoints para recibir solicitudes de mensajes
// * y actualizaciones de estado desde otros servicios
// */
//@RestController
//@RequestMapping("/api/integration")
//@RequiredArgsConstructor
//@Slf4j
//public class IntegrationController {
//
//    private final MessageQueueConsumerService queueConsumerService;
//
//    /**
//     * Endpoint para que otros servicios envíen solicitudes de mensajes
//     */
//    @PostMapping("/messages")
//    public ResponseEntity<MessageResponse> queueMessage(@Valid @RequestBody SendMessageRequest request) {
//        log.info("Recibida solicitud de integración para enviar mensaje a: {}", request.getRecipientPhone());
//
//        boolean added = queueConsumerService.queueSendMessageRequest(request);
//
//        if (added) {
//            return ResponseEntity.accepted().body(MessageResponse.builder()
//                    .recipientPhone(request.getRecipientPhone())
//                    .message("Solicitud de mensaje encolada correctamente")
//                    .build());
//        } else {
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(MessageResponse.builder()
//                    .recipientPhone(request.getRecipientPhone())
//                    .message("No se pudo encolar la solicitud, servicio ocupado")
//                    .build());
//        }
//    }
//
//    /**
//     * Endpoint para que otros servicios envíen actualizaciones de estado
//     */
//    @PostMapping("/status")
//    public ResponseEntity<MessageResponse> updateStatus(@Valid @RequestBody MessageStatusNotification notification) {
//        log.info("Recibida notificación de estado para mensaje: {}, estado: {}",
//                notification.getExternalId(), notification.getStatus());
//
//        boolean added = queueConsumerService.queueStatusNotification(notification);
//
//        if (added) {
//            return ResponseEntity.accepted().body(MessageResponse.builder()
//                    .message("Notificación de estado encolada correctamente")
//                    .build());
//        } else {
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(MessageResponse.builder()
//                    .message("No se pudo encolar la notificación, servicio ocupado")
//                    .build());
//        }
//    }
//}
