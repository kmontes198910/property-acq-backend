package com.kynsoft.invoiceservice.application.command.productcategory.update;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Objeto de solicitud para la actualización de una categoría de producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCategoryRequest {
    private UUID id;
    private String name;
    private String description;
    private Boolean status;
}
