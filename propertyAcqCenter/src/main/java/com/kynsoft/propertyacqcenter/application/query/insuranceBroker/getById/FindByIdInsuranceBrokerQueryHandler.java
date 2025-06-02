package com.kynsoft.propertyacqcenter.application.query.insuranceBroker.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.InsuranceBrokerResponse;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceBrokerService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdInsuranceBrokerQueryHandler implements IQueryHandler<FindByIdInsuranceBrokerQuery, InsuranceBrokerResponse> {

    private final IInsuranceBrokerService insuranceBrokerService;

    public FindByIdInsuranceBrokerQueryHandler(IInsuranceBrokerService insuranceBrokerService) {
        this.insuranceBrokerService = insuranceBrokerService;
    }

    @Override
    public InsuranceBrokerResponse handle(FindByIdInsuranceBrokerQuery query) {
        return new InsuranceBrokerResponse(this.insuranceBrokerService.findById(query.getId()));
    }
}
