package com.kynsoft.wamessaging.infrastructure.repository.command;

import com.kynsoft.wamessaging.infrastructure.entity.WhatsAppMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WhatsAppMessageWriteDataJPARepository extends JpaRepository<WhatsAppMessage, UUID> {
}
