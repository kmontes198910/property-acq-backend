package com.kynsoft.propertyacqcenter.application.query.insurance.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchInsuranceQueryHandler implements IQueryHandler<GetSearchInsuranceQuery, PaginatedResponse>{

    private final IInsuranceService insuranceService;

    public GetSearchInsuranceQueryHandler(IInsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Override
    public PaginatedResponse handle(GetSearchInsuranceQuery query) {

        return this.insuranceService.search(query.getPageable(),query.getFilter());
    }
}
