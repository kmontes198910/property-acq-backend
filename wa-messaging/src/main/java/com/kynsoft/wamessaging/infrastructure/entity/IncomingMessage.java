package com.kynsoft.wamessaging.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa un mensaje entrante recibido de WhatsApp
 */
@Entity
@Table(name = "wa_incoming_messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncomingMessage {

    @Id
    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "sender_phone", nullable = false)
    private String senderPhone;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "server_timestamp")
    private Long serverTimestamp;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "media_id")
    private String mediaId;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "caption")
    private String caption;
    
    @Column(name = "context_message_id")
    private String contextMessageId;

    @Column(name = "reply_to", columnDefinition = "TEXT")
    private String replyTo;

    @Column(name = "location_latitude")
    private Double latitude;

    @Column(name = "location_longitude")
    private Double longitude;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "contacts", columnDefinition = "TEXT")
    private String contacts;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Column(name = "processed", nullable = false)
    @Builder.Default
    private boolean processed = false;
}
