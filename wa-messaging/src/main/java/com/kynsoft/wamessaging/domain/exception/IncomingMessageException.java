package com.kynsoft.wamessaging.domain.exception;

import lombok.Getter;

/**
 * Excepción para errores relacionados con mensajes entrantes de WhatsApp
 */
@Getter
public class IncomingMessageException extends RuntimeException {

    private final String messageId;
    private final String errorCode;

    /**
     * Constructor con mensaje de error y ID de mensaje
     * 
     * @param message mensaje descriptivo del error
     * @param messageId ID del mensaje relacionado con el error
     */
    public IncomingMessageException(String message, String messageId) {
        super(message);
        this.messageId = messageId;
        this.errorCode = "INCOMING_MESSAGE_ERROR";
    }

    /**
     * Constructor con mensaje de error, causa, ID de mensaje y código de error
     * 
     * @param message mensaje descriptivo del error
     * @param cause causa raíz del error
     * @param messageId ID del mensaje relacionado con el error
     * @param errorCode código específico del error
     */
    public IncomingMessageException(String message, Throwable cause, String messageId, String errorCode) {
        super(message, cause);
        this.messageId = messageId;
        this.errorCode = errorCode;
    }
}
