package com.kynsoft.wamessaging.infrastructure.service;

import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import com.kynsoft.wamessaging.domain.entity.WhatsAppMessage;
import com.kynsoft.wamessaging.domain.service.WhatsAppMessageService;
import com.kynsoft.wamessaging.infrastructure.repository.WhatsAppMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio de mensajes de WhatsApp
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsAppMessageServiceImpl implements WhatsAppMessageService {
    
    private final WhatsAppMessageRepository messageRepository;
    
    @Override
    @Transactional
    public WhatsAppMessage saveMessage(WhatsAppMessage message) {
        log.info("Guardando mensaje de WhatsApp para el destinatario: {}", message.getRecipientPhone());
        return messageRepository.save(message);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<WhatsAppMessage> findById(UUID id) {
        log.debug("Buscando mensaje de WhatsApp con ID: {}", id);
        return messageRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<WhatsAppMessage> findByStatus(MessageStatus status) {
        log.debug("Buscando mensajes con estado: {}", status);
        return messageRepository.findByStatusOrderByCreatedAtAsc(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<WhatsAppMessage> findPendingMessages(int maxRetries) {
        log.debug("Buscando mensajes pendientes con máximo número de reintentos: {}", maxRetries);
        return messageRepository.findPendingAndRetryingMessages(maxRetries);
    }
    
    @Override
    @Transactional
    public WhatsAppMessage updateMessageStatus(UUID id, MessageStatus status, String externalId, String errorMessage) {
        log.debug("Actualizando estado del mensaje ID: {} a: {}", id, status);
        
        WhatsAppMessage message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mensaje no encontrado con ID: " + id));
        
        message.setStatus(status);
        
        if (externalId != null) {
            message.setExternalId(externalId);
        }
        
        if (errorMessage != null) {
            message.setErrorMessage(errorMessage);
        }
        
        // Actualizar fechas según el estado
        if (status == MessageStatus.SENT) {
            message.setSentAt(LocalDateTime.now());
        }
        
        return messageRepository.save(message);
    }
    
    @Override
    @Transactional
    public WhatsAppMessage incrementRetryCount(UUID id) {
        log.debug("Incrementando contador de reintentos para mensaje ID: {}", id);
        
        WhatsAppMessage message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mensaje no encontrado con ID: " + id));
        
        int currentRetries = message.getRetryCount() != null ? message.getRetryCount() : 0;
        message.setRetryCount(currentRetries + 1);
        
        return messageRepository.save(message);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<WhatsAppMessage> findByRecipientPhone(String recipientPhone) {
        log.debug("Buscando mensajes para destinatario: {}", recipientPhone);
        return messageRepository.findByRecipientPhone(recipientPhone);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<WhatsAppMessage> findByCreatedAtRange(LocalDateTime start, LocalDateTime end) {
        log.debug("Buscando mensajes en rango de fechas: {} - {}", start, end);
        return messageRepository.findByCreatedAtBetween(start, end);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<WhatsAppMessage> findByExternalId(String externalId) {
        log.debug("Buscando mensaje por ID externo: {}", externalId);
        return messageRepository.findByExternalId(externalId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<WhatsAppMessage> searchMessages(LocalDateTime startDate, LocalDateTime endDate, MessageStatus status, String phone, Pageable pageable) {
        log.debug("Buscando mensajes con filtros - desde: {}, hasta: {}, estado: {}, teléfono: {}", 
                startDate, endDate, status, phone);
        return messageRepository.searchMessages(startDate, endDate, status, phone, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<WhatsAppMessage> findMessagesToProcess(List<MessageStatus> statuses, int limit) {
        log.debug("Buscando próximos {} mensajes para procesar con estados: {}", limit, statuses);
        return messageRepository.findByStatusInOrderByCreatedAtAsc(statuses, PageRequest.of(0, limit));
    }
}