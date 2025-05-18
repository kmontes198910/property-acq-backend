package com.kynsoft.wamessaging.infrastructure.repository.query;

import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import com.kynsoft.wamessaging.infrastructure.entity.WhatsAppMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WhatsAppMessageReadDataJPARepository extends JpaRepository<WhatsAppMessage, UUID>, JpaSpecificationExecutor<WhatsAppMessage> {
    Page<WhatsAppMessage> findAll(Specification specification, Pageable pageable);

    /**
     * Busca mensajes por estado
     */
    List<WhatsAppMessage> findByStatus(MessageStatus status);

    /**
     * Busca mensajes por estado y ordena por fecha de creación
     */
    List<WhatsAppMessage> findByStatusOrderByCreatedAtAsc(MessageStatus status);

    /**
     * Busca mensajes pendientes o en reintentos con número de reintentos menor al límite
     */
    @Query("SELECT m FROM WhatsAppMessage m WHERE (m.status = 'PENDING' OR m.status = 'RETRYING') AND (m.retryCount < :maxRetries)")
    List<WhatsAppMessage> findPendingAndRetryingMessages(int maxRetries);

    /**
     * Busca mensajes para un número de teléfono específico
     */
    List<WhatsAppMessage> findByRecipientPhone(String recipientPhone);

    /**
     * Busca mensajes enviados en un periodo de tiempo
     */
    List<WhatsAppMessage> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Busca mensajes paginados por estado
     */
    Page<WhatsAppMessage> findByStatus(MessageStatus status, Pageable pageable);

    /**
     * Busca mensajes por ID externo (ID asignado por la API de WhatsApp)
     */
    Optional<WhatsAppMessage> findByExternalId(String externalId);

    /**
     * Busca mensajes con filtros dinámicos
     */
    @Query("SELECT m FROM WhatsAppMessage m WHERE " +
           "(:startDate IS NULL OR m.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR m.createdAt <= :endDate) AND " +
           "(:status IS NULL OR m.status = :status) AND " +
           "(:phone IS NULL OR m.recipientPhone LIKE %:phone%)")
    Page<WhatsAppMessage> searchMessages(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") MessageStatus status,
            @Param("phone") String phone,
            Pageable pageable);

    /**
     * Busca mensajes con estados específicos, limitados por cantidad y ordenados por fecha
     */
    @Query("SELECT m FROM WhatsAppMessage m WHERE m.status IN :statuses ORDER BY m.createdAt ASC")
    List<WhatsAppMessage> findByStatusInOrderByCreatedAtAsc(@Param("statuses") List<MessageStatus> statuses, Pageable pageable);
}
