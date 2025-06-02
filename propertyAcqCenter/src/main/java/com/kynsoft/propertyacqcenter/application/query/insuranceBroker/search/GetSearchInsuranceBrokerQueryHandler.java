package com.kynsoft.propertyacqcenter.application.query.insuranceBroker.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceBrokerService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchInsuranceBrokerQueryHandler implements IQueryHandler<GetSearchInsuranceBrokerQuery, PaginatedResponse>{

    private final IInsuranceBrokerService insuranceBrokerService;

    public GetSearchInsuranceBrokerQueryHandler(IInsuranceBrokerService insuranceBrokerService) {
        this.insuranceBrokerService = insuranceBrokerService;
    }

    @Override
    public PaginatedResponse handle(GetSearchInsuranceBrokerQuery query) {

        return this.insuranceBrokerService.search(query.getPageable(),query.getFilter());
    }
}
