package com.kynsof.evaluation.application.command.evaluationPatient.createSpecification;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.service.IEvaluationPatientService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CreateSpecificationEvaluationPatientCommandHandler implements ICommandHandler<CreateSpecificationEvaluationPatientCommand> {

    private final IEvaluationPatientService serviceImpl;
    private final IEvaluationService evaluationServiceImpl;

    public CreateSpecificationEvaluationPatientCommandHandler(IEvaluationPatientService serviceImpl,
                                                              IEvaluationService evaluationServiceImpl) {
        this.serviceImpl = serviceImpl;

        this.evaluationServiceImpl = evaluationServiceImpl;
    }

    @Override
    public void handle(CreateSpecificationEvaluationPatientCommand command) {
        EvaluationDto evaluationDto = this.evaluationServiceImpl.findByIds(command.getEvaluationId());
        EvaluationPatientExamDto evaluationPatientExamDto = new EvaluationPatientExamDto();
        evaluationPatientExamDto.setId(UUID.randomUUID());
        evaluationPatientExamDto.setEvaluation(evaluationDto);
        evaluationPatientExamDto.setExamDate(LocalDate.now());
        evaluationPatientExamDto.setExamenType(command.getExamenType());

          this.serviceImpl.createSpecification(evaluationPatientExamDto, command.getExamenListCode());
    }
}
