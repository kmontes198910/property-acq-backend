package com.kynsoft.wamessaging.infrastructure.repository;

import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import com.kynsoft.wamessaging.domain.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MessageRepositoryImpl implements MessageRepository {

    @Override
    public void updateMessageStatus(String messageId, MessageStatus status) {
        // TODO: Implementar la persistencia en base de datos aquí
        // Por ahora solo registramos en el log
        log.info("Actualizando estado del mensaje {} a {}", messageId, status);
    }
}
