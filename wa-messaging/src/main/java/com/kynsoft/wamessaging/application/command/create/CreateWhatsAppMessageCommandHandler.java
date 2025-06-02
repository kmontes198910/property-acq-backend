package com.kynsoft.wamessaging.application.command.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;

import com.kynsoft.wamessaging.domain.service.WhatsAppMessageService;
import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import com.kynsoft.wamessaging.infrastructure.entity.WhatsAppMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateWhatsAppMessageCommandHandler implements ICommandHandler<CreateWhatsAppMessageCommand> {
    private final ObjectMapper objectMapper;
    private final WhatsAppMessageService messageService;

    @Override
    public void handle(CreateWhatsAppMessageCommand command) {
        try {
            log.info("Encolando mensaje para el destinatario: {}", command.getRecipientPhone());

            // Crear y guardar el mensaje con estado PENDING
            WhatsAppMessage message = WhatsAppMessage.builder()
                    .recipientPhone(command.getRecipientPhone())
                    .recipientName(command.getRecipientName())
                    .messageContent(objectMapper.writeValueAsString(command.getMessageContent()))// ✔️ genera JSON válido)
                    .messageType(command.getMessageType())
                    .templateName(command.getTemplateName())
                    .status(MessageStatus.PENDING)
                    .retryCount(0)
                    .createdAt(LocalDateTime.now())
                    .build();

            message = messageService.saveMessage(message);
            log.info("Mensaje encolado con ID: {}", message.getId());
            command.setId(message.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
