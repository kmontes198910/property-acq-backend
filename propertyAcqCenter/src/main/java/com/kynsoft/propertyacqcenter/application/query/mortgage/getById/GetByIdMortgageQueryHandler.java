package com.kynsoft.propertyacqcenter.application.query.mortgage.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.MortgageResponse;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdMortgageQueryHandler implements IQueryHandler<GetByIdMortgageQuery, MortgageResponse>{

    private final IMortgageService mortgageService;

    public GetByIdMortgageQueryHandler(IMortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    @Override
    public MortgageResponse handle(GetByIdMortgageQuery query) {

        return new MortgageResponse(this.mortgageService.findById(query.getId()));
    }
}
