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
public class ValidationRequestDto {
    private byte[] document;              // Documento firmado en formato binario
    private String documentName;          // Nombre del documento
}