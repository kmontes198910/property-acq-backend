package com.kynsoft.invoiceservice.application.query.productcategory.get;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase de respuesta para consultas de categorías de productos
 */
//@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCategoryResponse implements IResponse {
    private UUID id;
    private String name;
    private String description;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductCategoryResponse(ProductCategoryDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}
