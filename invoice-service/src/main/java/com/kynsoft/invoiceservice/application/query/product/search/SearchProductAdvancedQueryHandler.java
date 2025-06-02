package com.kynsoft.invoiceservice.application.query.product.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la búsqueda avanzada de productos con paginación y filtrado
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchProductAdvancedQueryHandler implements IQueryHandler<SearchProductAdvancedQuery, PaginatedResponse> {

    private final IProductService productService;

    @Override
    public PaginatedResponse handle(SearchProductAdvancedQuery query) {
        log.info("Manejando búsqueda avanzada de productos");
        return productService.searchAdvanced(query.getPageable(), query.getFilter());
    }
}
