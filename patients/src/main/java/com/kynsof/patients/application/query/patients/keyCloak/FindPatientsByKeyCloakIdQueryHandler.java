package com.kynsof.patients.application.query.patients.keyCloak;

import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class FindPatientsByKeyCloakIdQueryHandler implements IQueryHandler<FindPatientsByKeyCloakIdQuery, FindPatientsByKeyCloakIdResponse>  {

    private final IPatientsService serviceImpl;

    public FindPatientsByKeyCloakIdQueryHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public FindPatientsByKeyCloakIdResponse handle(FindPatientsByKeyCloakIdQuery query) {
        PatientByIdDto patient = serviceImpl.findByKeyCloakId(query.getId());
        return new FindPatientsByKeyCloakIdResponse(patient);
    }
}
