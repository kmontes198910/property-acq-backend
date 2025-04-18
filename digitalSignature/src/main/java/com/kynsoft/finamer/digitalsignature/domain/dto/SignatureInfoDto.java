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
public class SignatureInfoDto {
    private String signerName;            // Nombre del firmante
    private String signatureDate;         // Fecha de la firma
    private VisibleSignatureDto position; // Posición de la firma visible si existe
    private boolean visible;              // Indica si la firma es visible o no
    private boolean valid;                // Indica si la firma es válida
}