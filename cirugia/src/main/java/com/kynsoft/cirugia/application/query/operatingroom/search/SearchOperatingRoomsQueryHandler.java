package com.kynsoft.cirugia.application.query.operatingroom.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SearchOperatingRoomsQueryHandler implements IQueryHandler<SearchOperatingRoomsQuery, PaginatedResponse> {

    private final IOperatingRoomService service;

    public SearchOperatingRoomsQueryHandler(IOperatingRoomService service) {
        this.service = service;
    }

    @Override
    public PaginatedResponse handle(SearchOperatingRoomsQuery query) {
        log.info("Searching operating rooms with filter criteria and query: {}", query.getQuery());
        
        return service.search(query.getPageable(), query.getFilter());
    }
}