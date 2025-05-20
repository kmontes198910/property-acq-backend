package com.kynsoft.wamessaging.application.command.webhook;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Mensaje resultante del procesamiento de un webhook de WhatsApp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessWebhookMessage implements ICommandMessage {
    private String payload;
    private Map<String, String> result = new HashMap<>();
    
    /**
     * Constructor con payload
     */
    public ProcessWebhookMessage(String payload) {
        this.payload = payload;
        this.result = new HashMap<>();
        this.result.put("status", "success");
    }
    
    /**
     * Agregar información de procesamiento
     */
    public void addProcessedInfo(String key, String value) {
        this.result.put(key, value);
    }
}
