package com.kynsoft.invoiceservice.application.command.invoice.generate.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {
    private String formaPago;
    private String descripcion;
    private BigDecimal plazo;
    private String unidadTiempo;
    private BigDecimal monto;
}