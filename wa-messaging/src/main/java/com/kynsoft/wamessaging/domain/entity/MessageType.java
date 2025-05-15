package com.kynsoft.wamessaging.domain.entity;

/**
 * Enumeración que define los tipos de mensajes de WhatsApp soportados
 */
public enum MessageType {
    TEXT,         // Mensaje de texto simple
    TEMPLATE,     // Mensaje basado en una plantilla
    IMAGE,        // Mensaje con imagen
    DOCUMENT,     // Mensaje con documento
    AUDIO,        // Mensaje de audio
    VIDEO,        // Mensaje de video
    LOCATION      // Mensaje con ubicación
}
