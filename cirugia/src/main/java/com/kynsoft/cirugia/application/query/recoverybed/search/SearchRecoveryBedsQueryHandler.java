package com.kynsoft.cirugia.application.query.recoverybed.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchRecoveryBedsQueryHandler implements IQueryHandler<SearchRecoveryBedsQuery, PaginatedResponse> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    public PaginatedResponse handle(SearchRecoveryBedsQuery query) {
        log.info("Searching recovery beds with filter criteria: {}", query.getFilterCriteria());
        
        return recoveryBedService.search(query.getPageable(), query.getFilterCriteria());
    }
}