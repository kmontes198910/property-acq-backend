package com.kynsof.patients.application.query.dashboard.countPatient;

import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class CountPatientQueryHandler implements IQueryHandler<CountPatientQuery, CountPatientResponse>  {

    private final IPatientsService serviceImpl;

    public CountPatientQueryHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public CountPatientResponse handle(CountPatientQuery query) {
        Long patient = serviceImpl.countPatient();
        return new CountPatientResponse(patient);
    }
}
