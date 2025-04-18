package com.kynsoft.finamer.digitalsignature.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationResponseDto {
    private boolean valid;               // Indicador de validez de la firma
    private List<SignatureInfoDto> signatures; // Información de las firmas presentes en el documento
}