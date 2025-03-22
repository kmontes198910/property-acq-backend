package com.kynsof.evaluation.application.command.evaluationPatient.update;

import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.service.IEvaluationPatientService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateEvaluationPatientCommandHandler implements ICommandHandler<UpdateEvaluationPatientCommand> {

    private final IEvaluationPatientService serviceImpl;

    public UpdateEvaluationPatientCommandHandler(IEvaluationPatientService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(UpdateEvaluationPatientCommand command) {
        EvaluationPatientExamDto evaluationDto = this.serviceImpl.findByIds(command.getId());

        serviceImpl.update(evaluationDto,command.getAnswers());
    }
}
