package com.kynsoft.invoiceservice.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    private BigDecimal totalWithTax;

    @Builder.Default
    private List<InvoiceDetailTaxDto> taxes = new ArrayList<>();
}
