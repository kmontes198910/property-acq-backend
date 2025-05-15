package com.kynsoft.wamessaging.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa un mensaje de WhatsApp
 */
@Entity
@Table(name = "wa_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recipient_phone", nullable = false)
    private String recipientPhone;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "message_content", nullable = false, length = 4000)
    private String messageContent;
    
    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    
    @Column(name = "external_id")
    private String externalId;
    
    @Column(name = "error_message")
    private String errorMessage;
    
    @Column(name = "retry_count")
    private Integer retryCount;
    
    @Column(name = "media_url")
    private String mediaUrl;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = MessageStatus.PENDING;
        }
        if (retryCount == null) {
            retryCount = 0;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
