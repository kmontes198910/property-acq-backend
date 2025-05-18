package com.kynsoft.invoiceservice.application.query.productcategory.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para transferir datos de categorías de productos entre capas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
