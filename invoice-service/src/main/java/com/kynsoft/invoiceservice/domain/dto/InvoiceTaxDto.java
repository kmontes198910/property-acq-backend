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
public class InvoiceTaxDto {
    private UUID id;
    private String code;
    private String description;
    private BigDecimal rate;
    private BigDecimal baseAmount;
    private BigDecimal taxAmount;
}
