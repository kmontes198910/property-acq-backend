package com.kynsoft.invoiceservice.application.query.product.getById;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para transferir datos de productos entre capas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private String mainCode;
    private String auxiliarCode;
    private BigDecimal unitPrice;
    private Integer stock;
    private String taxCode;
    private BigDecimal taxPercentage;
    private String iceCode;
    private BigDecimal icePercentage;
    private String rentCode;
    private BigDecimal rentTaxPercentage;
    private String productType;
    private Boolean isService;
    private Boolean isActive;
    private UUID categoryId;
    private String categoryName;
    private UUID createdBy;
    private UUID updatedBy;
}
