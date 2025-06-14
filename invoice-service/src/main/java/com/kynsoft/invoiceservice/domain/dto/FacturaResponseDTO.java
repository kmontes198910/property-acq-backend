package com.kynsoft.invoiceservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaResponseDTO {
    private String mensaje;
    private String estado;
    private String claveAcceso;
}