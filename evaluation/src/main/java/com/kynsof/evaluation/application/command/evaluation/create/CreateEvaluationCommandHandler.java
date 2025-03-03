package com.kynsof.evaluation.application.command.evaluation.create;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.evaluation.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateEvaluationCommandHandler implements ICommandHandler<CreateEvaluationCommand> {

    private final IEvaluationService serviceImpl;
    private final IPatientsService patientsService;
    private final PatientHttpUUIDService patientHttpUUIDService;

    public CreateEvaluationCommandHandler(IEvaluationService serviceImpl,
            IPatientsService patientsService,
            PatientHttpUUIDService patientHttpUUIDService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    public void handle(CreateEvaluationCommand command) {
        PatientDto patientDto = null;
        try {
            patientDto = this.patientsService.findById(command.getPatient());
        } catch (Exception e) {
            PatientHttp patient = patientHttpUUIDService.sendGetBookingHttpRequest(command.getPatient());
            patientDto = new PatientDto(
                    patient.getId(),
                    patient.getIdentification(),
                    patient.getName(),
                    patient.getLastName(),
                    Status.valueOf(patient.getStatus()),
                    !patient.getBirthDate().equals("") ? LocalDate.parse(patient.getBirthDate()) : null
            );
            this.patientsService.create(patientDto);
        }
        serviceImpl.create(new EvaluationDto(
                command.getId(),
                patientDto,
                command.getConsultationReason(),
                command.getMedicalHistory(),
                command.getPhysicalExam(),
                command.getMedicalSpeciality()
        ));
    }
}
