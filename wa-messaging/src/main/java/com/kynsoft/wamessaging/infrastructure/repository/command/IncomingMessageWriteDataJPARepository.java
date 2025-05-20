package com.kynsoft.wamessaging.infrastructure.repository.command;

import com.kynsoft.wamessaging.infrastructure.entity.IncomingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de escritura para mensajes entrantes de WhatsApp
 */
@Repository
public interface IncomingMessageWriteDataJPARepository extends JpaRepository<IncomingMessage, String> {
}
