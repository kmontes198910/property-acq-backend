package com.kynsoft.finamer.digitalsignature.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignResponseDto {
    private byte[] signedDocument;        // Documento firmado en formato binario
    private VisibleSignatureDto signaturePosition; // Posición donde se ha aplicado la firma (si aplica)
    private String signerName;            // Nombre del firmante
    private String signatureDate;         // Fecha de la firma
}