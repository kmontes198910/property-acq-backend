package com.kynsof.patients.application.query.patientsInsurance.search;

import com.kynsof.patients.domain.service.IPatientInsuranceService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPatientInsuranceQueryHandler implements IQueryHandler<GetSearchPatientInsuranceQuery, PaginatedResponse>{

    private final IPatientInsuranceService serviceImpl;

    public GetSearchPatientInsuranceQueryHandler(IPatientInsuranceService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchPatientInsuranceQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
