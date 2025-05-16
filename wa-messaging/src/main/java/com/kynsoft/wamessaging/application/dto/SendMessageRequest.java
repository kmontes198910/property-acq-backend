package com.kynsoft.wamessaging.application.dto;

import com.kynsoft.wamessaging.domain.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * DTO para la creación de mensajes de WhatsApp
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    
    @NotEmpty(message = "El número de teléfono del destinatario es requerido")
    @Pattern(regexp = "^\\d{12}$", message = "El número debe tener 12 dígitos, incluyendo código de país")
    private String recipientPhone;
    
    private String messageType = "template";  // Por defecto usamos template
    
    @NotEmpty(message = "El nombre de la plantilla es requerido")
    private String templateName;
    
    private TemplateData templateData;
    
    @Data
    public static class TemplateData {
        private String header;  // Para el texto del encabezado
        private List<String> body;  // Para los parámetros del body
        private String button;  // Para la URL del botón
    }
    
    public Map<String, Object> toTemplateDataMap() {
        Map<String, Object> dataMap = new HashMap<>();
        if (templateData != null) {
            if (templateData.getHeader() != null) {
                dataMap.put("header", templateData.getHeader());
            }
            if (templateData.getBody() != null) {
                dataMap.put("body", templateData.getBody());
            }
            if (templateData.getButton() != null) {
                dataMap.put("button", templateData.getButton());
            }
        }
        return dataMap;
    }
}
