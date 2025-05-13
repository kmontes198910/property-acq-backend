package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSignRequest {
    private String document;           // Documento a firmar en bytes
    private String documentName;       // Nombre del documento (para logs)
    private String certificatePassword; // Contraseña del certificado
    private String certificateP12Id;   // ID del certificado en base de datos (opcional)
    private VisibleSignatureRequest visibleSignature; // Información de firma visible (opcional)
    private String reason;             // Razón de la firma (opcional)
    private String location;           // Ubicación de la firma (opcional)
}
