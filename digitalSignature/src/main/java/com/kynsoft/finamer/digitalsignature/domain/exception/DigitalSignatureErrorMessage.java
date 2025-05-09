package com.kynsoft.finamer.digitalsignature.domain.exception;

import lombok.Getter;

/**
 * Mensajes de error específicos para el microservicio de firma digital
 */
@Getter
public enum DigitalSignatureErrorMessage {
    CERTIFICATE_NOT_FOUND("DSERR-001", "Certificado de firma digital no encontrado"),
    CERTIFICATE_EXPIRED("DSERR-002", "El certificado de firma digital ha expirado"),
    INVALID_CERTIFICATE("DSERR-003", "El certificado de firma digital no es válido"),
    CERTIFICATE_PASSWORD_INCORRECT("DSERR-004", "La contraseña del certificado es incorrecta"),
    CERTIFICATE_ALREADY_EXISTS("DSERR-005", "Ya existe un certificado de firma digital con ese nombre para el usuario"),
    BUSINESS_NOT_FOUND("DSERR-006", "Negocio no encontrado"),
    USER_NOT_FOUND("DSERR-007", "Usuario no encontrado"), CERTIFICATE_NOT_BELONG_TO_USER("DSERR-007", "El certificado no pertenece al usuario"),;

    private final String code;
    private final String message;

    DigitalSignatureErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}