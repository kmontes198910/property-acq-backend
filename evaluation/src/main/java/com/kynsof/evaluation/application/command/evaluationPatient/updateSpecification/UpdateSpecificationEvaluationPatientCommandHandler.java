package com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.service.IEvaluationPatientService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class UpdateSpecificationEvaluationPatientCommandHandler implements ICommandHandler<UpdateSpecificationEvaluationPatientCommand> {

    private final IEvaluationPatientService serviceImpl;
    private final IEvaluationService evaluationServiceImpl;

    public UpdateSpecificationEvaluationPatientCommandHandler(IEvaluationPatientService serviceImpl,
                                                              IEvaluationService evaluationServiceImpl) {
        this.serviceImpl = serviceImpl;

        this.evaluationServiceImpl = evaluationServiceImpl;
    }

    @Override
    public void handle(UpdateSpecificationEvaluationPatientCommand command) {
        EvaluationPatientExamDto evaluationDto = this.serviceImpl.findByIds(command.getId());


          this.serviceImpl.updateSpecification(evaluationDto, command.getExamenListCode());
    }
}
