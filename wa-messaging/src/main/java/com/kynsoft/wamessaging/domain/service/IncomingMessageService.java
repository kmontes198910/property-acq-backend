package com.kynsoft.wamessaging.domain.service;

import com.kynsoft.wamessaging.infrastructure.entity.IncomingMessage;
import com.kynsoft.wamessaging.infrastructure.repository.command.IncomingMessageWriteDataJPARepository;
import com.kynsoft.wamessaging.infrastructure.repository.query.IncomingMessageReadDataJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar los mensajes entrantes de WhatsApp
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IncomingMessageService {

    private final IncomingMessageWriteDataJPARepository writeRepository;
    private final IncomingMessageReadDataJPARepository readRepository;

    /**
     * Guarda un nuevo mensaje entrante
     */
    @Transactional
    public IncomingMessage saveIncomingMessage(IncomingMessage message) {
        log.info("Guardando mensaje entrante con ID: {}", message.getMessageId());
        return writeRepository.save(message);
    }
    /**
     * Obtiene un mensaje por su ID
     */
    @Transactional(readOnly = true)
    public Optional<IncomingMessage> getMessageById(String messageId) {
        return readRepository.findById(messageId);
    }

    /**
     * Obtiene mensajes paginados
     */
    @Transactional(readOnly = true)
    public Page<IncomingMessage> getAllMessages(Pageable pageable) {
        return readRepository.findAll(pageable);
    }

    /**
     * Obtiene mensajes de un remitente específico
     */
    @Transactional(readOnly = true)
    public List<IncomingMessage> getMessagesBySender(String senderPhone) {
        return readRepository.findBySenderPhoneOrderByReceivedAtDesc(senderPhone);
    }

    /**
     * Obtiene mensajes entre dos fechas
     */
    @Transactional(readOnly = true)
    public Page<IncomingMessage> getMessagesBetweenDates(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return readRepository.findByReceivedAtBetweenOrderByReceivedAtDesc(start, end, pageable);
    }
    
    /**
     * Marca un mensaje como procesado
     */
    @Transactional
    public void markAsProcessed(String messageId) {
        readRepository.findById(messageId).ifPresent(message -> {
            message.setProcessed(true);
            writeRepository.save(message);
        });
    }
    
    /**
     * Obtiene mensajes no procesados
     */
    @Transactional(readOnly = true)
    public List<IncomingMessage> getUnprocessedMessages() {
        return readRepository.findByProcessedFalseOrderByReceivedAtAsc();
    }
}
