package com.kynsoft.propertyacqcenter.application.query.mortgage.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchMortgageQueryHandler implements IQueryHandler<GetSearchMortgageQuery, PaginatedResponse>{

    private final IMortgageService mortgageService;

    public GetSearchMortgageQueryHandler(IMortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    @Override
    public PaginatedResponse handle(GetSearchMortgageQuery query) {

        return this.mortgageService.search(query.getPageable(),query.getFilter());
    }
}
