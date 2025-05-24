package com.kynsoft.invoiceservice.application.query.tax.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.service.ITaxService;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la búsqueda avanzada de impuestos con paginación y filtrado
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchTaxAdvancedQueryHandler implements IQueryHandler<SearchTaxAdvancedQuery, PaginatedResponse> {

    private final ITaxService taxService;

    @Override
    public PaginatedResponse handle(SearchTaxAdvancedQuery query) {
        log.info("Manejando búsqueda avanzada de impuestos");
        
        // Si se especifica un tipo de impuesto en la consulta
        if (query.getQuery() != null && !query.getQuery().isEmpty()) {
            try {
                // Intenta convertir el string a un enum TaxType
                Tax.TaxType taxType = Tax.TaxType.valueOf(query.getQuery().toUpperCase());
                return taxService.findByTaxType(taxType, query.getPageable());
            } catch (IllegalArgumentException e) {
                // Si no es un tipo válido, continúa con la búsqueda normal
                log.info("Tipo de impuesto no válido, realizando búsqueda con filtros generales");
            }
        }
        
        // Búsqueda estándar con filtros
        return taxService.searchAdvanced(query.getPageable(), query.getFilter());
    }
}
