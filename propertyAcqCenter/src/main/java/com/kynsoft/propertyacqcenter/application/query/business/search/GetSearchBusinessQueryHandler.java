package com.kynsoft.propertyacqcenter.application.query.business.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchBusinessQueryHandler implements IQueryHandler<GetSearchBusinessQuery, PaginatedResponse>{

    private final IBusinessService businessService;

    public GetSearchBusinessQueryHandler(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public PaginatedResponse handle(GetSearchBusinessQuery query) {

        return this.businessService.search(query.getPageable(),query.getFilter());
    }
}
