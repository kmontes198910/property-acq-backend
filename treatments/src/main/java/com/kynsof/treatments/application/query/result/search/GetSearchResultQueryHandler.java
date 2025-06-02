package com.kynsof.treatments.application.query.result.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.application.service.ResultServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetSearchResultQueryHandler implements IQueryHandler<GetSearchResultQuery, PaginatedResponse> {

    private final ResultServiceImpl resultService;

    @Override
    public PaginatedResponse handle(GetSearchResultQuery query) {

        return resultService.search(
                query.getPageable(),
                query.getFilter(),
                query.getQuery()
        );
    }
}