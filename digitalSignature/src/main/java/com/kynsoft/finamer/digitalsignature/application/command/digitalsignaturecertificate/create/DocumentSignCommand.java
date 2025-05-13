package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSignCommand implements ICommand {
    private byte[] signedDocument;        // Documento firmado en bytes
    private String document;              // Documento a firmar en formato base64
    private String documentName;          // Nombre del documento (para logs)
    private String certificatePassword;   // Contraseña del certificado
    private String certificateP12Id;      // ID del certificado en base de datos (opcional)
    private VisibleSignatureRequest visibleSignature; // Información de firma visible (opcional)
    private String reason;                // Razón de la firma (opcional)
    private String location;              // Ubicación de la firma (opcional)
    private UUID businessId;              // ID del negocio (opcional)
    private String createdBy;             // Usuario que realiza la firma
    @Setter
    private DocumentSignMessage responseMessage; // Mensaje de respuesta completo
    
    public static DocumentSignCommand fromRequest(DocumentSignRequest request, String userId) {
        return DocumentSignCommand.builder()
                .document(request.getDocument())
                .documentName(request.getDocumentName())
                .certificatePassword(request.getCertificatePassword())
                .certificateP12Id(request.getCertificateP12Id())
                .visibleSignature(request.getVisibleSignature())
                .reason(request.getReason())
                .location(request.getLocation())
                .businessId(request.getBusinessId())
                .createdBy(userId)
                .build();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return responseMessage != null ? responseMessage : new DocumentSignMessage(signedDocument, null, null, null, null, documentName, null);
    }

}