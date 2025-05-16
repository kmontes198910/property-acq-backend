package com.kynsoft.wamessaging.application.service;

import com.kynsoft.wamessaging.application.dto.MessageResponse;
import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import com.kynsoft.wamessaging.application.dto.WhatsAppApiResponse;
import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import com.kynsoft.wamessaging.domain.entity.MessageType;
import com.kynsoft.wamessaging.domain.entity.WhatsAppMessage;
import com.kynsoft.wamessaging.domain.service.WhatsAppApiClient;
import com.kynsoft.wamessaging.domain.service.WhatsAppMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para la coordinación del envío de mensajes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageCoordinatorService {

    private final WhatsAppMessageService messageService;
    private final WhatsAppApiClient apiClient;

    /**
     * Procesa una solicitud de envío de mensaje
     */
    @Transactional
    public MessageResponse queueMessage(SendMessageRequest request) {
        log.info("Encolando mensaje para el destinatario: {}", request.getRecipientPhone());

        // Crear y guardar el mensaje con estado PENDING
        WhatsAppMessage message = WhatsAppMessage.builder()
                .recipientPhone(request.getRecipientPhone())
                .recipientName(request.getRecipientName())
                .messageContent(request.getMessageContent())
                .messageType(request.getMessageType())
                .mediaUrl(request.getMediaUrl())
                .status(MessageStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();

        message = messageService.saveMessage(message);

        // Convertir a DTO de respuesta
        return convertToDto(message);
    }

    /**
     * Envía un mensaje específico a través de la API de WhatsApp
     */
    @Transactional
    public MessageResponse sendMessage(UUID messageId) {
        log.info("Enviando mensaje con ID: {}", messageId);

        Optional<WhatsAppMessage> messageOpt = messageService.findById(messageId);

        if (messageOpt.isEmpty()) {
            log.error("Mensaje no encontrado: {}", messageId);
            return null;
        }

        WhatsAppMessage message = messageOpt.get();

        // Marcar el mensaje como en procesamiento
        message = messageService.updateMessageStatus(messageId, MessageStatus.PROCESSING, null, null);

        // Enviar el mensaje según su tipo
        WhatsAppApiResponse apiResponse;

        try {
            apiResponse = switch (message.getMessageType()) {
                case TEXT -> apiClient.sendTextMessage(
                        message.getRecipientPhone(),
                        message.getMessageContent()
                );
                case TEMPLATE ->
                    // Aquí se asume que messageContent contiene el nombre de la plantilla
                    // y que se ha configurado un servicio adicional para obtener los datos de la plantilla
                        apiClient.sendTemplateMessage(
                                message.getRecipientPhone(),
                                message.getMessageContent(),
                                null // Aquí se deberían pasar los datos de la plantilla
                        );
                case IMAGE, DOCUMENT, AUDIO, VIDEO -> apiClient.sendMediaMessage(
                        message.getRecipientPhone(),
                        message.getMessageContent(),
                        message.getMediaUrl(),
                        message.getMessageType()
                );
                default ->
                        throw new IllegalArgumentException("Tipo de mensaje no soportado: " + message.getMessageType());
            };

            // Actualizar el estado del mensaje según la respuesta de la API
            if (apiResponse.isSuccessful()) {
                message = messageService.updateMessageStatus(
                        messageId,
                        MessageStatus.SENT,
                        apiResponse.getId(),
                        null
                );
            } else {
                message = messageService.updateMessageStatus(
                        messageId,
                        MessageStatus.FAILED,
                        null,
                        apiResponse.getErrorMessage()
                );

                // Si hay un error, incrementar contador de reintentos para futuros intentos
                messageService.incrementRetryCount(messageId);
            }

        } catch (Exception e) {
            log.error("Error al enviar mensaje WhatsApp", e);
            message = messageService.updateMessageStatus(
                    messageId,
                    MessageStatus.FAILED,
                    null,
                    "Error interno: " + e.getMessage()
            );

            // Si hay una excepción, incrementar contador de reintentos
            messageService.incrementRetryCount(messageId);
        }

        return convertToDto(message);
    }

    /**
     * Actualiza el estado de un mensaje basado en una notificación de webhook
     */
    @Transactional
    public MessageResponse updateMessageStatus(String externalId, MessageStatus newStatus, String errorMessage) {
        log.info("Actualizando estado del mensaje externo {} a {}", externalId, newStatus);

        Optional<WhatsAppMessage> messageOpt = messageService.findByExternalId(externalId);

        if (messageOpt.isEmpty()) {
            log.warn("No se encontró mensaje con ID externo: {}", externalId);
            return null;
        }

        WhatsAppMessage message = messageOpt.get();
        message = messageService.updateMessageStatus(message.getId(), newStatus, externalId, errorMessage);

        return convertToDto(message);
    }

    /**
     * Consulta un mensaje por su ID
     */
    public MessageResponse getMessage(UUID messageId) {
        log.info("Consultando mensaje con ID: {}", messageId);

        Optional<WhatsAppMessage> messageOpt = messageService.findById(messageId);

        if (messageOpt.isEmpty()) {
            log.warn("Mensaje no encontrado: {}", messageId);
            return null;
        }

        return convertToDto(messageOpt.get());
    }

    /**
     * Busca mensajes según varios criterios
     */
    public Page<MessageResponse> searchMessages(
            LocalDateTime startDate,
            LocalDateTime endDate,
            MessageStatus status,
            String phone,
            Pageable pageable) {

        log.info("Buscando mensajes con filtros - desde: {}, hasta: {}, estado: {}, teléfono: {}",
                startDate, endDate, status, phone);

        Page<WhatsAppMessage> messages = messageService.searchMessages(startDate, endDate, status, phone, pageable);

        return messages.map(this::convertToDto);
    }

    /**
     * Reintenta el envío de un mensaje fallido
     */
    @Transactional
    public MessageResponse retryMessage(UUID messageId) {
        log.info("Reintentando envío del mensaje: {}", messageId);

        Optional<WhatsAppMessage> messageOpt = messageService.findById(messageId);

        if (messageOpt.isEmpty()) {
            log.error("Mensaje no encontrado para reintento: {}", messageId);
            throw new IllegalArgumentException("Mensaje no encontrado: " + messageId);
        }

        WhatsAppMessage message = messageOpt.get();

        // Solo se puede reintentar mensajes fallidos
        if (message.getStatus() != MessageStatus.FAILED) {
            log.error("No se puede reintentar un mensaje que no esté en estado FAILED. Estado actual: {}", message.getStatus());
            throw new IllegalStateException("Solo se pueden reintentar mensajes en estado FAILED");
        }

        // Marcar como pendiente para que el procesador lo tome
        message = messageService.updateMessageStatus(messageId, MessageStatus.RETRYING, null, null);

        return convertToDto(message);
    }

    /**
     * Cancela un mensaje pendiente
     */
    @Transactional
    public MessageResponse cancelMessage(UUID messageId) {
        log.info("Cancelando mensaje: {}", messageId);

        Optional<WhatsAppMessage> messageOpt = messageService.findById(messageId);

        if (messageOpt.isEmpty()) {
            log.error("Mensaje no encontrado para cancelación: {}", messageId);
            throw new IllegalArgumentException("Mensaje no encontrado: " + messageId);
        }

        WhatsAppMessage message = messageOpt.get();

        // Solo se pueden cancelar mensajes pendientes o en reintento
        List<MessageStatus> cancelableStatuses = Arrays.asList(
                MessageStatus.PENDING,
                MessageStatus.RETRYING);

        if (!cancelableStatuses.contains(message.getStatus())) {
            log.error("No se puede cancelar un mensaje que no esté en estado PENDING o RETRYING. Estado actual: {}", message.getStatus());
            throw new IllegalStateException("Solo se pueden cancelar mensajes en estado PENDING o RETRYING");
        }

        // Marcar como cancelado
        message = messageService.updateMessageStatus(messageId, MessageStatus.CANCELLED, null, "Mensaje cancelado manualmente");

        return convertToDto(message);
    }

    /**
     * Procesa mensajes pendientes en lote
     */
    @Transactional
    public List<MessageResponse> processNextBatch(int batchSize) {
        log.info("Procesando siguiente lote de mensajes pendientes, tamaño: {}", batchSize);

        List<MessageStatus> statusesToProcess = Arrays.asList(
                MessageStatus.PENDING,
                MessageStatus.RETRYING);

        List<WhatsAppMessage> messagesToProcess = messageService.findMessagesToProcess(statusesToProcess, batchSize);

        return messagesToProcess.stream()
                .map(message -> sendMessage(message.getId()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Convierte una entidad de mensaje a un DTO de respuesta
     */
    private MessageResponse convertToDto(WhatsAppMessage message) {
        return MessageResponse.builder()
                .id(message.getId())
                .recipientPhone(message.getRecipientPhone())
                .recipientName(message.getRecipientName())
                .messageContent(message.getMessageContent())
                .messageType(message.getMessageType().name())
                .status(message.getStatus())
                .externalId(message.getExternalId())
                .errorMessage(message.getErrorMessage())
                .retryCount(message.getRetryCount())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .sentAt(message.getSentAt())
                .build();
    }
}
