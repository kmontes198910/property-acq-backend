package com.kynsoft.wamessaging.example;

import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import com.kynsoft.wamessaging.domain.entity.MessageType;
import com.kynsoft.wamessaging.infrastructure.client.WhatsAppMessagingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppMessageExample {

    private final WhatsAppMessagingClient messagingClient;

    @Autowired
    public WhatsAppMessageExample(WhatsAppMessagingClient messagingClient) {
        this.messagingClient = messagingClient;
    }

    /**
     * Envía un mensaje de texto simple via WhatsApp
     */
    public void sendSimpleMessage() {
        SendMessageRequest request = SendMessageRequest.builder()
                .recipientPhone("593983825630")
                .messageContent("Hola, este es un mensaje de prueba desde la API de WhatsApp.")
                .messageType(MessageType.TEXT)
                .build();

        messagingClient.sendMessage(request);
    }
}
