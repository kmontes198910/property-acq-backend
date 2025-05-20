package com.kynsoft.wamessaging.infrastructure.entity;

/**
 * Enumeración que define los posibles estados de un mensaje de WhatsApp según la API v22
 */
public enum MessageStatus {
    /**
     * Mensaje pendiente de envío
     */
    PENDING,

    /**
     * Mensaje en proceso de envío
     */
    PROCESSING,

    /**
     * Mensaje enviado exitosamente a la API de WhatsApp
     */
    SENT,

    /**
     * Mensaje entregado al dispositivo del destinatario
     */
    DELIVERED,

    /**
     * Mensaje leído por el destinatario
     */
    READ,

    /**
     * Error durante el envío del mensaje
     */
    FAILED,

    /**
     * Estado desconocido del mensaje
     */
    UNKNOWN,

    /**
     * Envío del mensaje cancelado
     */
    CANCELLED,

    /**
     * Reintentando envío después de un fallo
     */
    RETRYING
}
