package com.kynsoft.medicaltest.application.query.labtest.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la consulta de búsqueda de exámenes de laboratorio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchLabTestQueryHandler implements IQueryHandler<SearchLabTestQuery, PaginatedResponse> {

    private final ILabTestService labTestService;

    @Override
    public PaginatedResponse handle(SearchLabTestQuery query) {
        log.info("Buscando exámenes de laboratorio con filtro: {}", query.getFilter());
        return labTestService.search(query.getPageable(), query.getFilter());
    }
}
