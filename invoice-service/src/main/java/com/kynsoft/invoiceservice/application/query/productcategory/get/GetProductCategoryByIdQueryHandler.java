package com.kynsoft.invoiceservice.application.query.productcategory.get;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.invoiceservice.domain.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador para la consulta de categoría de productos por ID
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetProductCategoryByIdQueryHandler implements IQueryHandler<GetProductCategoryByIdQuery, ProductCategoryResponse> {

    private final IProductCategoryService productCategoryService;

    @Override
    public ProductCategoryResponse handle(GetProductCategoryByIdQuery query) {
        log.info("Buscando categoría de producto con ID: {}", query.getId());
        
        ProductCategoryDto dto = productCategoryService.findById(query.getId());
        
        return ProductCategoryResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
