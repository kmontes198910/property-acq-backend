package com.kynsoft.medicaltest.application.query.labTestItemRequest.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.service.ILabTestItemRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchLabTestItemRequestQueryHandler implements IQueryHandler<SearchLabTestItemRequestQuery, PaginatedResponse> {

    private final ILabTestItemRequestService labTestItemRequestService;

    @Override
    public PaginatedResponse handle(SearchLabTestItemRequestQuery query) {
        return labTestItemRequestService.search(query.getPageable(), query.getFilter());
    }
}
