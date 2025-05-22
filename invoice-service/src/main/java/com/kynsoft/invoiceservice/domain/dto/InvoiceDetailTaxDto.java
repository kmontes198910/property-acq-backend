package com.kynsoft.invoiceservice.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetailTaxDto {
    private UUID id;
    private String code;
    private String percentageCode;
    private BigDecimal rate;
    private BigDecimal taxableBase;
    private BigDecimal value;
}
