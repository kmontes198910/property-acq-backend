package com.kynsof.evaluation.application.query.patients.getByIdHttp;

import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.evaluation.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class FindHttpPatientsByIdQueryHandler implements IQueryHandler<FindHttpPatientsByIdQuery, PatientHttp>  {

    private final PatientHttpUUIDService serviceImpl;
    private final IPatientsService patientsService;

    public FindHttpPatientsByIdQueryHandler(PatientHttpUUIDService serviceImpl,
                                            IPatientsService patientsService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
    }

    @Override
    public PatientHttp handle(FindHttpPatientsByIdQuery query) {
        PatientHttp patient = serviceImpl.sendGetBookingHttpRequest(query.getId());
        this.patientsService.create(new PatientDto(
                patient.getId(), 
                patient.getIdentification(), 
                patient.getName(), 
                patient.getLastName(), 
                Status.valueOf(patient.getStatus()), 
                !patient.getBirthDate().equals("") ? LocalDate.parse(patient.getBirthDate()) : null
        ));
        return patient;
    }
}
