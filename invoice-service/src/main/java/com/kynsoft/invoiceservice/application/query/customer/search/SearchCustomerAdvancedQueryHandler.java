package com.kynsoft.invoiceservice.application.query.customer.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la búsqueda avanzada de clientes con paginación y filtrado
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchCustomerAdvancedQueryHandler implements IQueryHandler<SearchCustomerAdvancedQuery, PaginatedResponse> {

    private final ICustomerService customerService;

    @Override
    public PaginatedResponse handle(SearchCustomerAdvancedQuery query) {
        log.info("Manejando búsqueda avanzada de clientes");
        return customerService.search(query.getPageable(), query.getFilter());
    }
}
