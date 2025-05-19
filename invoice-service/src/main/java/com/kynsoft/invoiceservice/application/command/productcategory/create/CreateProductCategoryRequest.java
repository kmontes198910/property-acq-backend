package com.kynsoft.invoiceservice.application.command.productcategory.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de solicitud para la creación de una categoría de producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCategoryRequest {
    
    @NotNull(message = "El nombre de la categoría no puede ser nulo")
    @NotEmpty(message = "El nombre de la categoría no puede estar vacío")
    private String name;
    
    private String description;
    
    private Boolean status;
}
