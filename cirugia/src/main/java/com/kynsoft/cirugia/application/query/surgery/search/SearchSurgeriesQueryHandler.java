package com.kynsoft.cirugia.application.query.surgery.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SearchSurgeriesQueryHandler implements IQueryHandler<SearchSurgeriesQuery, PaginatedResponse> {

    private final ISurgeryService service;

    public SearchSurgeriesQueryHandler(ISurgeryService service) {
        this.service = service;
    }

    @Override
    public PaginatedResponse handle(SearchSurgeriesQuery query) {
        log.info("Searching surgeries with filter criteria and query: {}", query.getQuery());
        
        return service.search(query.getPageable(), query.getFilter());
    }
}