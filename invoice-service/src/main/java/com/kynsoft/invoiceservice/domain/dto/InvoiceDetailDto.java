package com.kynsoft.invoiceservice.domain.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetailDto {
    private UUID id;
    private Integer lineNumber;
    private String mainCode;
    private String auxiliaryCode;
    private String description;
    private BigDecimal quantity;
    private String unitOfMeasure;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal subtotal;
    private String ivaCode;
    private BigDecimal ivaRate;
    private BigDecimal ivaAmount;
    private String iceCode;
    private BigDecimal iceRate;
    private BigDecimal iceAmount;
    private BigDecimal totalWithTax;
}
