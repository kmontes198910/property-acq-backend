package com.kynsoft.invoiceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private String formaPago;
    private String descripcion;
    private BigDecimal total;
    private Integer plazo;
    private String unidadTiempo;
}