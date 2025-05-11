package com.kynsoft.invoiceservice.application.command.product.update;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private String description;
    private String mainCode;
    private String auxiliaryCode;
    private BigDecimal price;
    private Integer stock;
    private String taxCode;
    private BigDecimal taxPercentage;
    private Boolean isService;
    private Boolean status;
    private UUID categoryId;
}