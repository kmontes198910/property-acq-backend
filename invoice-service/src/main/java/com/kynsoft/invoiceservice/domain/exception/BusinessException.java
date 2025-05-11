package com.kynsoft.invoiceservice.domain.exception;

import lombok.Getter;

/**
 * Excepción personalizada para errores de negocio en el microservicio de firma digital.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final String message;

    /**
     * Constructor para crear una excepción de negocio con código y mensaje.
     * 
     * @param code Código de error
     * @param message Mensaje de error
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * Constructor para crear una excepción de negocio basada en un mensaje de error predefinido.
     * 
     * @param errorMessage Enumeración con el error predefinido
     */
    public BusinessException(DomainErrorInvoiceMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
    }

    /**
     * Constructor para crear una excepción de negocio basada en un mensaje de error predefinido 
     * con información adicional.
     * 
     * @param errorMessage Enumeración con el error predefinido
     * @param additionalInfo Información adicional para incluir en el mensaje
     */
    public BusinessException(DomainErrorInvoiceMessage errorMessage, String additionalInfo) {
        super(errorMessage.getMessage() + ": " + additionalInfo);
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage() + ": " + additionalInfo;
    }

    /**
     * Constructor para crear una excepción de negocio con una causa subyacente.
     * 
     * @param errorMessage Enumeración con el error predefinido
     * @param cause Excepción que causó este error
     */
    public BusinessException(DomainErrorInvoiceMessage errorMessage, Throwable cause) {
        super(errorMessage.getMessage(), cause);
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
    }
}