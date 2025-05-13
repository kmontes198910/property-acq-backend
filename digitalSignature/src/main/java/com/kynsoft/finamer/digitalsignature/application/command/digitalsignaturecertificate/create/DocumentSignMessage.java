package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentSignMessage implements ICommandMessage {
    private byte[] signedDocument;        // Documento firmado en formato binario
    private String signedDocumentBase64;  // Documento firmado en formato base64
    private String signerName;            // Nombre del firmante
    private String signatureDate;         // Fecha de la firma
    private String filePath;              // Ruta donde se guardó el documento firmado (si aplica)
    private String documentName;          // Nombre del documento
    private String contentType;           // Tipo de contenido (MIME type)
}