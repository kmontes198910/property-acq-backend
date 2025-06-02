package com.kynsoft.wamessaging.application.command.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.wamessaging.infrastructure.entity.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateWhatsAppMessageCommand implements ICommand {
    private  UUID id;
    private final String recipientPhone;

    private final String recipientName;
    private final Object messageContent;
    private final MessageType messageType;

    private final String templateName;

    public CreateWhatsAppMessageCommand(String recipientPhone, String recipientName, Object messageContent, MessageType messageType, String templateName) {
        this.recipientPhone = recipientPhone;
        this.recipientName = recipientName;
        this.messageContent = messageContent;
        this.messageType = messageType;
        this.templateName = templateName;
    }


    public static CreateWhatsAppMessageCommand fromRequest(CreateWhatsAppMessageRequest request) {
        return new CreateWhatsAppMessageCommand(
                request.getRecipientPhone(),
                request.getRecipientName(),
                request.getMessageContent(),
                request.getMessageType(),
                request.getTemplateName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateWhatsAppMessageMessage(id);
    }
}
