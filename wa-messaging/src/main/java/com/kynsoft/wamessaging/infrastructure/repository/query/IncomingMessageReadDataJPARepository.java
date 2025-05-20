package com.kynsoft.wamessaging.infrastructure.repository.query;

import com.kynsoft.wamessaging.infrastructure.entity.IncomingMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio de lectura para mensajes entrantes de WhatsApp
 */
@Repository
public interface IncomingMessageReadDataJPARepository extends JpaRepository<IncomingMessage, String> {
    
    /**
     * Busca mensajes por número de teléfono del remitente
     */
    List<IncomingMessage> findBySenderPhoneOrderByReceivedAtDesc(String senderPhone);
    
    /**
     * Busca mensajes por rango de fechas con paginación
     */
    Page<IncomingMessage> findByReceivedAtBetweenOrderByReceivedAtDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    /**
     * Busca mensajes no procesados
     */
    List<IncomingMessage> findByProcessedFalseOrderByReceivedAtAsc();
}
