package com.kynsoft.medicaltest.application.query.labTestRequest.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchLabTestRequestQueryHandler implements IQueryHandler<SearchLabTestRequestQuery, PaginatedResponse> {

    private final ILabTestRequestService labTestRequestService;

    @Override
    public PaginatedResponse handle(SearchLabTestRequestQuery query) {
        log.info("Buscando exámenes de laboratorio con filtro: {}", query.getFilter());
        return labTestRequestService.search(query.getPageable(), query.getFilter());
    }
}
