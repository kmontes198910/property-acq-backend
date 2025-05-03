package com.kynsoft.cirugia.application.query.diagnosis.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchDiagnosesQueryHandler implements IQueryHandler<SearchDiagnosesQuery, PaginatedResponse> {

    private final IDiagnosisService diagnosisService;

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse handle(SearchDiagnosesQuery query) {
        log.info("Searching diagnoses with filters");
        return diagnosisService.search(query.getPageable(), query.getFilter());
    }
}