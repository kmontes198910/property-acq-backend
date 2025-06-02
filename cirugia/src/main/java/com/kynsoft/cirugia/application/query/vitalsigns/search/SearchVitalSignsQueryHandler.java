package com.kynsoft.cirugia.application.query.vitalsigns.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.IVitalSignsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchVitalSignsQueryHandler implements IQueryHandler<SearchVitalSignsQuery, PaginatedResponse> {

    private final IVitalSignsService vitalSignsService;

    @Override
    public PaginatedResponse handle(SearchVitalSignsQuery query) {
        log.info("Searching vital signs with filter: {}", query.getFilter());
        return vitalSignsService.search(query.getPageable(), query.getFilter());
    }
}