package com.kynsoft.wamessaging.application.dto;

import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar la respuesta de un mensaje enviado o consultado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    
    private UUID id;
    private String recipientPhone;
    private String recipientName;
    private String messageContent;
    private String messageType;
    private MessageStatus status;
    private String externalId;
    private String errorMessage;
    private Integer retryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime sentAt;
    
    // Campo opcional para mensajes informativos al usuario
    private String message;
}
