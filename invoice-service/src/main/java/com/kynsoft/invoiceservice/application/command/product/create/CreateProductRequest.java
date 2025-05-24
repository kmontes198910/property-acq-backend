package com.kynsoft.invoiceservice.application.command.product.create;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String description;
    private String mainCode;
    private String auxiliaryCode;
    private BigDecimal price;
    private Integer stock;
    private String taxCode;
    private BigDecimal taxPercentage;
    private String iceCode;
    private BigDecimal icePercentage;
    private String rentCode;
    private BigDecimal rentTaxPercentage;
    private String productType;
    private Boolean isService;
    private UUID categoryId;
}