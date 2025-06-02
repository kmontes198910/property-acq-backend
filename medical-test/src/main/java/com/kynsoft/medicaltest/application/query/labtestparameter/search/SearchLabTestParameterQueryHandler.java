package com.kynsoft.medicaltest.application.query.labtestparameter.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la consulta de búsqueda de parámetros de exámenes de laboratorio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SearchLabTestParameterQueryHandler implements IQueryHandler<SearchLabTestParameterQuery, PaginatedResponse> {

    private final ILabTestParameterService labTestParameterService;

    @Override
    public PaginatedResponse handle(SearchLabTestParameterQuery query) {
        log.info("Buscando parámetros de exámenes de laboratorio con filtro: {}", query.getFilter());
        return labTestParameterService.search(query.getPageable(), query.getFilter());
    }
}
