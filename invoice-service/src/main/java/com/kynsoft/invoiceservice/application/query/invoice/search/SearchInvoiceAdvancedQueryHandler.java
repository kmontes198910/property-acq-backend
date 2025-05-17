package com.kynsoft.invoiceservice.application.query.invoice.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la búsqueda avanzada de facturas con paginación y filtrado
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchInvoiceAdvancedQueryHandler implements IQueryHandler<SearchInvoiceAdvancedQuery, PaginatedResponse> {

    private final IInvoiceService invoiceService;

    @Override
    public PaginatedResponse handle(SearchInvoiceAdvancedQuery query) {
        log.info("Manejando búsqueda avanzada de facturas");
        return invoiceService.searchAdvanced(query.getPageable(), query.getFilter());
    }
}
