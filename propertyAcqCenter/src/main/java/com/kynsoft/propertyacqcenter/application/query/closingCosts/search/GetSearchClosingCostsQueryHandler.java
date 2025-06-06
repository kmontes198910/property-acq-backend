package com.kynsoft.propertyacqcenter.application.query.closingCosts.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IClosingCostsService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchClosingCostsQueryHandler implements IQueryHandler<GetSearchClosingCostsQuery, PaginatedResponse>{

    private final IClosingCostsService closingCostsService;

    public GetSearchClosingCostsQueryHandler(IClosingCostsService closingCostsService) {
        this.closingCostsService = closingCostsService;
    }

    @Override
    public PaginatedResponse handle(GetSearchClosingCostsQuery query) {

        return this.closingCostsService.search(query.getPageable(),query.getFilter());
    }
}
