package com.kynsof.evaluation.application.command.evaluationPatient.create;

import com.kynsof.evaluation.domain.dto.DoctorDto;
import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.evaluation.domain.service.IDoctorService;
import com.kynsof.evaluation.domain.service.IEvaluationPatientService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.evaluation.infrastructure.service.http.DoctorHttpUUIDService;
import com.kynsof.evaluation.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CreateEvaluationPatientCommandHandler implements ICommandHandler<CreateEvaluationPatientCommand> {

    private final IEvaluationPatientService serviceImpl;
    private final IEvaluationService evaluationServiceImpl;

    public CreateEvaluationPatientCommandHandler(IEvaluationPatientService serviceImpl,
                                                 IEvaluationService evaluationServiceImpl) {
        this.serviceImpl = serviceImpl;

        this.evaluationServiceImpl = evaluationServiceImpl;
    }

    @Override
    public void handle(CreateEvaluationPatientCommand command) {
        EvaluationDto evaluationDto = this.evaluationServiceImpl.findByIds(command.getEvaluationId());
        EvaluationPatientExamDto evaluationPatientExamDto = new EvaluationPatientExamDto();
        evaluationPatientExamDto.setId(UUID.randomUUID());
        evaluationPatientExamDto.setEvaluation(evaluationDto);
        evaluationPatientExamDto.setExamDate(LocalDate.now());

          this.serviceImpl.create(evaluationPatientExamDto, command.getExamenListCode(), command.getExamenType());
    }
}
