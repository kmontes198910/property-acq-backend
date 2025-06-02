package com.kynsoft.invoiceservice.application.query.product.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.invoiceservice.infrastructure.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements IResponse {
    private UUID id;
    private String name;
    private String description;
    private String mainCode;
    private String auxiliaryCode;
    private BigDecimal price;
    private String taxCode;
    private BigDecimal taxPercentage;
    private String iceCode;
    private BigDecimal icePercentage;
    private String productType;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private CategoryInfo category;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryInfo {
        private UUID id;
        private String name;
    }

    public static ProductResponse fromEntity(Product product) {
        ProductResponseBuilder builder = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .mainCode(product.getMainCode())
                .auxiliaryCode(product.getAuxiliaryCode())
                .price(product.getPrice())
                .taxCode(product.getTaxCode())
                .taxPercentage(product.getTaxPercentage())
                .iceCode(product.getIceCode())
                .icePercentage(product.getIcePercentage())
                .productType(product.getProductType())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .createdBy(product.getCreatedBy())
                .updatedBy(product.getUpdatedBy());

        if (product.getCategory() != null) {
            builder.category(CategoryInfo.builder()
                    .id(product.getCategory().getId())
                    .name(product.getCategory().getName())
                    .build());
        }

        return builder.build();
    }
}