package com.kynsoft.wamessaging.application.query.getMessage;

import com.kynsoft.wamessaging.infrastructure.entity.MessageType;
import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para representar un mensaje entrante en las respuestas de la API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMessageResponse {
    private String messageId;
    private String senderPhone;
    private String senderName;
    private LocalDateTime receivedAt;
    private Long serverTimestamp;
    private MessageType messageType;
    private MessageStatus status;
    private String content;
    private String mediaUrl;
    private String mimeType;
    private String caption;
    private String contextMessageId;
    private String replyTo;
    private Double latitude;
    private Double longitude;
    private String locationName;
    private String locationAddress;
    private String contacts;
}
