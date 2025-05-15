package com.kynsoft.wamessaging.application.controller;

import com.kynsoft.wamessaging.application.dto.MessageResponse;
import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import com.kynsoft.wamessaging.domain.entity.WhatsAppMessage;
import com.kynsoft.wamessaging.domain.service.WhatsAppMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las operaciones de mensajería WhatsApp
 */
@RestController
@RequestMapping("/api/whatsapp")
@RequiredArgsConstructor
@Slf4j
public class WhatsAppController {

    private final MessageCoordinatorService messageCoordinatorService;
    private final WhatsAppMessageService whatsAppMessageService;

    /**
     * Envía un mensaje de WhatsApp
     *
     * @param request Datos del mensaje a enviar
     * @return Respuesta con el ID del mensaje creado
     */
    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        log.info("Recibida solicitud para enviar mensaje a: {}", request.getRecipientPhone());
        
        WhatsAppMessage message = messageCoordinatorService.queueMessage(request);
        
        MessageResponse response = MessageResponse.builder()
                .id(message.getId())
                .status(message.getStatus().name())
                .message("Mensaje puesto en cola para envío")
                .build();
                
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
    
    /**
     * Envía mensajes en lote a múltiples destinatarios
     *
     * @param requests Lista de solicitudes de mensajes
     * @return Lista de respuestas de mensajes creados
     */
    @PostMapping("/messages/batch")
    public ResponseEntity<List<MessageResponse>> sendBatchMessages(@Valid @RequestBody List<SendMessageRequest> requests) {
        log.info("Recibida solicitud para enviar {} mensajes en lote", requests.size());
        
        List<MessageResponse> responses = requests.stream()
                .map(messageCoordinatorService::queueMessage)
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .status(message.getStatus().name())
                        .message("Mensaje puesto en cola para envío")
                        .build())
                .collect(Collectors.toList());
                
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
    }
    
    /**
     * Obtiene el estado de un mensaje
     *
     * @param id ID del mensaje
     * @return Detalles del mensaje
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageResponse> getMessageStatus(@PathVariable UUID id) {
        log.info("Consultando estado del mensaje: {}", id);
        
        return whatsAppMessageService.findById(id)
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .status(message.getStatus().name())
                        .recipientPhone(message.getRecipientPhone())
                        .createdAt(message.getCreatedAt())
                        .sentAt(message.getSentAt())
                        .errorMessage(message.getErrorMessage())
                        .externalId(message.getExternalId())
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Busca mensajes según varios criterios
     *
     * @param startDate Fecha de inicio opcional
     * @param endDate Fecha de fin opcional
     * @param status Estado opcional
     * @param phone Número de teléfono opcional
     * @param pageable Configuración de paginación
     * @return Página de mensajes que coinciden con los criterios
     */
    @GetMapping("/messages")
    public ResponseEntity<Page<MessageResponse>> searchMessages(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) MessageStatus status,
            @RequestParam(required = false) String phone,
            Pageable pageable) {
        
        log.info("Buscando mensajes con filtros - desde: {}, hasta: {}, estado: {}, teléfono: {}", 
                startDate, endDate, status, phone);
        
        Page<WhatsAppMessage> messages = messageCoordinatorService.searchMessages(startDate, endDate, status, phone, pageable);
        
        Page<MessageResponse> response = messages.map(message -> MessageResponse.builder()
                .id(message.getId())
                .status(message.getStatus().name())
                .recipientPhone(message.getRecipientPhone())
                .recipientName(message.getRecipientName())
                .messageType(message.getMessageType().name())
                .createdAt(message.getCreatedAt())
                .sentAt(message.getSentAt())
                .errorMessage(message.getErrorMessage())
                .retryCount(message.getRetryCount())
                .build());
                
        return ResponseEntity.ok(response);
    }
    
    /**
     * Reintentar el envío de un mensaje que ha fallado
     *
     * @param id ID del mensaje
     * @return Respuesta con el estado del reintento
     */
    @PostMapping("/messages/{id}/retry")
    public ResponseEntity<MessageResponse> retryMessage(@PathVariable UUID id) {
        log.info("Solicitando reintento de envío para el mensaje: {}", id);
        
        try {
            WhatsAppMessage message = messageCoordinatorService.retryMessage(id);
            
            MessageResponse response = MessageResponse.builder()
                    .id(message.getId())
                    .status(message.getStatus().name())
                    .message("Mensaje puesto en cola para reintento")
                    .build();
                    
            return ResponseEntity.accepted().body(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Error al reintentar mensaje: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            log.error("Estado incorrecto para reintento: {}", e.getMessage());
            
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    
    /**
     * Cancelar un mensaje pendiente
     *
     * @param id ID del mensaje
     * @return Resultado de la cancelación
     */
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<MessageResponse> cancelMessage(@PathVariable UUID id) {
        log.info("Solicitando cancelación del mensaje: {}", id);
        
        try {
            messageCoordinatorService.cancelMessage(id);
            
            return ResponseEntity.ok(MessageResponse.builder()
                    .id(id)
                    .message("Mensaje cancelado correctamente")
                    .build());
                    
        } catch (IllegalArgumentException e) {
            log.error("Error al cancelar mensaje: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            log.error("Estado incorrecto para cancelación: {}", e.getMessage());
            
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
}
