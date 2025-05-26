package com.kynsoft.invoiceservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePaymentDto {

    private UUID id;
    private String paymentType;
    private String reference;
    private BigDecimal plazo;
    private String unidadTiempo;
    private BigDecimal amount;
}
