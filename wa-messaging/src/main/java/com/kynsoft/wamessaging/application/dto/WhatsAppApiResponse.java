package com.kynsoft.wamessaging.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de la API de WhatsApp
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppApiResponse {
    
    private String messagingProduct;
    private String contacts;
    private String messages;
    private String id;
    private String status;
    private String timestamp;
    private String errorMessage;
    private String errorCode;
    private boolean successful;
}
