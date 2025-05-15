package com.kynsoft.wamessaging.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para notificaciones sobre el estado de los mensajes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageStatusNotification {
    
    private UUID messageId;
    private String externalId;
    private String status;
    private String timestamp;
    private String errorMessage;
}
