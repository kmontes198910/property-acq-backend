package com.kynsof.patients.application.query.patientsInsurance.getById;

import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.domain.service.IPatientInsuranceService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class FindPatientsInsuranceByIdQueryHandler implements IQueryHandler<FindPatientsInsuranceByIdQuery, PatientsInsuranceByIdResponse>  {

    private final IPatientInsuranceService serviceImpl;

    public FindPatientsInsuranceByIdQueryHandler(IPatientInsuranceService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PatientsInsuranceByIdResponse handle(FindPatientsInsuranceByIdQuery query) {
        PatientInsuranceDto patient = serviceImpl.findById(query.getId());
        return new PatientsInsuranceByIdResponse(patient);
    }
}
