package com.kynsoft.wamessaging.domain.service;

import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import com.kynsoft.wamessaging.infrastructure.entity.WhatsAppMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz para el servicio de mensajes de WhatsApp
 */
public interface WhatsAppMessageService {
    
    /**
     * Guarda un mensaje de WhatsApp
     */
    WhatsAppMessage saveMessage(WhatsAppMessage message);
    
    /**
     * Busca un mensaje por su ID
     */
    Optional<WhatsAppMessage> findById(UUID id);
    
    /**
     * Busca mensajes por estado
     */
    List<WhatsAppMessage> findByStatus(MessageStatus status);
    
    /**
     * Busca mensajes pendientes para envío
     */
    List<WhatsAppMessage> findPendingMessages(int maxRetries);
    
    /**
     * Actualiza el estado de un mensaje
     */
    WhatsAppMessage updateMessageStatus(UUID id, MessageStatus status, String externalId, String errorMessage);
    
    /**
     * Incrementa el contador de reintentos de un mensaje
     */
    WhatsAppMessage incrementRetryCount(UUID id);
    
    /**
     * Busca mensajes para un destinatario específico
     */
    List<WhatsAppMessage> findByRecipientPhone(String recipientPhone);
    
    /**
     * Busca mensajes creados en un rango de fechas
     */
    List<WhatsAppMessage> findByCreatedAtRange(LocalDateTime start, LocalDateTime end);
    
    /**
     * Busca un mensaje por su ID externo (asignado por la API de WhatsApp)
     */
    Optional<WhatsAppMessage> findByExternalId(String externalId);
    
    /**
     * Busca mensajes por diferentes criterios con paginación
     */
    Page<WhatsAppMessage> searchMessages(
            LocalDateTime startDate, 
            LocalDateTime endDate, 
            MessageStatus status, 
            String phone, 
            Pageable pageable);
    
    /**
     * Busca los próximos mensajes a procesar según una lista de estados
     */
    List<WhatsAppMessage> findMessagesToProcess(List<MessageStatus> statuses, int limit);
}
