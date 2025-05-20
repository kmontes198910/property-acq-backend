package com.kynsoft.wamessaging.application.command.webhook;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Comando para procesar un webhook entrante de WhatsApp
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessWebhookCommand implements ICommand {
    private String payload;
    
    @Override
    public ICommandMessage getMessage() {
        return new ProcessWebhookMessage(payload);
    }
}
