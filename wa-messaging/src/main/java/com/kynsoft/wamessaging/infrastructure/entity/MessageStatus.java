package com.kynsoft.wamessaging.infrastructure.entity;

/**
 * Enumeración que define los posibles estados de un mensaje de WhatsApp
 */
public enum MessageStatus {
    PENDING,       // Pendiente de envío
    PROCESSING,    // En proceso de envío
    SENT,          // Enviado a la API de WhatsApp
    DELIVERED,     // Entregado al destinatario
    READ,          // Leído por el destinatario
    FAILED,        // Error en el envío
    UNKNOWN, CANCELLED, RETRYING       // Reintentando envío después de un fallo
}
