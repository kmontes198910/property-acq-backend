package com.kynsoft.invoiceservice.application.query.Issuer.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la búsqueda avanzada de emisores de facturas con paginación y filtrado
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchInvoiceIssuerAdvancedQueryHandler implements IQueryHandler<SearchInvoiceIssuerAdvancedQuery, PaginatedResponse> {

    private final IInvoiceIssuerService invoiceIssuerService;

    @Override
    public PaginatedResponse handle(SearchInvoiceIssuerAdvancedQuery query) {
        log.info("Manejando búsqueda avanzada de emisores de facturas");
        return invoiceIssuerService.search(query.getPageable(), query.getFilter());
    }
}
