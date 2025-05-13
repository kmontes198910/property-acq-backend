package com.kynsoft.finamer.digitalsignature.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de firma de un documento
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignRequestDto {
    private String document;           // Documento a firmar en bytes
    private String documentName;       // Nombre del documento (para logs)
    private String certificateAlias;   // Alias del certificado a usar (opcional)
    private String certificatePassword; // Contraseña del certificado
    private String certificateP12Id;   // ID del certificado en base de datos (opcional)
    private VisibleSignatureDto visibleSignature; // Información de firma visible (opcional)
    private String reason;             // Razón de la firma (opcional)
    private String location;           // Ubicación de la firma (opcional)
}