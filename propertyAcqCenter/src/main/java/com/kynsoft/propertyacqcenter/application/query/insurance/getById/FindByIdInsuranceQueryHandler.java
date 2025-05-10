package com.kynsoft.propertyacqcenter.application.query.insurance.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.InsuranceResponse;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdInsuranceQueryHandler implements IQueryHandler<FindByIdInsuranceQuery, InsuranceResponse> {

    private final IInsuranceService insuranceService;

    public FindByIdInsuranceQueryHandler(IInsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Override
    public InsuranceResponse handle(FindByIdInsuranceQuery query) {
        return new InsuranceResponse(this.insuranceService.findById(query.getId()));
    }
}
