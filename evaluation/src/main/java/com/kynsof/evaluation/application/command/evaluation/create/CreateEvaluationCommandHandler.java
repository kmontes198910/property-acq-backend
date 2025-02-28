package com.kynsof.evaluation.application.command.evaluation.create;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

@Component
public class CreateEvaluationCommandHandler implements ICommandHandler<CreateEvaluationCommand> {

    private final IEvaluationService serviceImpl;
    private final IPatientsService patientsService;

    public CreateEvaluationCommandHandler(IEvaluationService serviceImpl,
            IPatientsService patientsService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
    }

    @Override
    public void handle(CreateEvaluationCommand command) {
        PatientDto patientDto = this.patientsService.findById(command.getPatient());
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
