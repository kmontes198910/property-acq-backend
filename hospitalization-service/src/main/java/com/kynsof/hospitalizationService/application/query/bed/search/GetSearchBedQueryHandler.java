package com.kynsof.hospitalizationService.application.query.bed.search;

import com.kynsof.hospitalizationService.domain.service.IBedService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchBedQueryHandler implements IQueryHandler<GetSearchBedQuery, PaginatedResponse>{

    private final IBedService serviceImpl;

    public GetSearchBedQueryHandler(IBedService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchBedQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
