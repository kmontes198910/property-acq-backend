package com.kynsoft.invoiceservice.application.query.productcategory.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la búsqueda avanzada de categorías de productos con paginación y filtrado
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchProductCategoryAdvancedQueryHandler implements IQueryHandler<SearchProductCategoryAdvancedQuery, PaginatedResponse> {

    private final IProductCategoryService productCategoryService;

    @Override
    public PaginatedResponse handle(SearchProductCategoryAdvancedQuery query) {
        log.info("Manejando búsqueda avanzada de categorías de productos");
        return productCategoryService.search(query.getPageable(), query.getFilter());
    }
}
