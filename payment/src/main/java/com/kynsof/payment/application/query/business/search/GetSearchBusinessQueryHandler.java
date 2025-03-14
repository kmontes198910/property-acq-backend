package com.kynsof.payment.application.query.business.search;

import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchBusinessQueryHandler implements IQueryHandler<GetSearchBusinessQuery, PaginatedResponse>{
    private final IBusiness service;
    
    public GetSearchBusinessQueryHandler(IBusiness service) {
        this.service = service;
    }

    @Override
    public PaginatedResponse handle(GetSearchBusinessQuery query) {

        return this.service.search(query.getPageable(),query.getFilter());
    }
}
