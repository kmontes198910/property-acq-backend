package com.kynsoft.wamessaging.domain.service;

import com.kynsoft.wamessaging.application.dto.WhatsAppApiResponse;
import com.kynsoft.wamessaging.domain.entity.MessageType;

import java.util.Map;
import java.util.UUID;

/**
 * Interfaz para el servicio que se comunica con la API de WhatsApp
 */
public interface WhatsAppApiClient {
    
    /**
     * Envía un mensaje de texto
     */
    WhatsAppApiResponse sendTextMessage(String recipientPhone, String message);
    
    /**
     * Envía un mensaje basado en plantilla
     */
    WhatsAppApiResponse sendTemplateMessage(String recipientPhone,String recipientName, String templateName, Map<String,Object> templateData);
    
    /**
     * Envía un mensaje con contenido multimedia
     */
    WhatsAppApiResponse sendMediaMessage(String recipientPhone, String caption,  MessageType mediaType);
    
    /**
     * Verifica el estado de un mensaje
     */
    WhatsAppApiResponse checkMessageStatus(UUID messageId);
}
