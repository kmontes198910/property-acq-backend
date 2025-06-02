package com.kynsoft.wamessaging.domain.repository;

import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;

public interface MessageRepository {
    void updateMessageStatus(String messageId, MessageStatus status);
}
