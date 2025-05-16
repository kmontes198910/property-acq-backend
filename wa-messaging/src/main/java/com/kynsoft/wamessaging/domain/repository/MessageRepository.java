package com.kynsoft.wamessaging.domain.repository;

import com.kynsoft.wamessaging.domain.entity.MessageStatus;

public interface MessageRepository {
    void updateMessageStatus(String messageId, MessageStatus status);
}
