package com.kynsoft.wamessaging.application.query.getMessage;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.wamessaging.application.dto.MessageResponse;
import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetMessageResponse implements IResponse {
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

    public GetMessageResponse(MessageResponse message) {
        this.id = message.getId();
        this.recipientPhone = message.getRecipientPhone();
        this.recipientName = message.getRecipientName();
        this.messageContent = message.getMessageContent();
        this.messageType = message.getMessageType();
        this.status = message.getStatus();
        this.externalId = message.getExternalId();
        this.errorMessage = message.getErrorMessage();
        this.retryCount = message.getRetryCount();
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
        this.sentAt = message.getSentAt();
        this.message = message.getMessage();
    }
}
