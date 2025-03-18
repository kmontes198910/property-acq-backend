package com.kynsof.patients.application.query.patients.getByIdHttpReplicate;

import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import org.springframework.stereotype.Component;

@Component
public class FindHttpPatientsByIdQueryHandler implements IQueryHandler<FindHttpPatientsByIdQuery, PatientHttp>  {

    private final IPatientsService serviceImpl;

    public FindHttpPatientsByIdQueryHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PatientHttp handle(FindHttpPatientsByIdQuery query) {
        PatientByIdDto patient = serviceImpl.findById(query.getId());
        String birthDate = "";
        if (patient.getContactInfoDto() != null) {
            birthDate = patient.getContactInfoDto().getBirthdayDate() != null ? patient.getContactInfoDto().getBirthdayDate().toString() : "";
        }
        return new PatientHttp(
                patient.getId(), 
                patient.getIdentification(), 
                patient.getName(), 
                patient.getLastName(), 
                patient.getGender().name(), 
                patient.getStatus().name(), 
                birthDate,
                patient.getContactInfoDto() != null ? patient.getContactInfoDto().getEmail() : null,
                patient.getContactInfoDto() != null ? patient.getContactInfoDto().getTelephone() : null,
                patient.getProfession()
        );
    }
}
