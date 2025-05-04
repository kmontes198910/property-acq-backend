package com.kynsoft.cirugia.application.query.treatment.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchTreatmentsQueryHandler implements IQueryHandler<SearchTreatmentsQuery, PaginatedResponse> {

    private final ITreatmentRepository treatmentRepository;

    @Override
    public PaginatedResponse handle(SearchTreatmentsQuery query) {
        log.info("Buscando tratamientos con criterios de filtro: {}", query.getFilterCriteria());
        
        return treatmentRepository.search(query.getPageable(), query.getFilterCriteria());
    }
}