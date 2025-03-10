package com.kynsof.evaluation.application.command.evaluationPatient.createSpecification;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateSpecificationEvaluationPatientCommand implements ICommand {
    private UUID patient;
    private UUID evaluationId;
    private List<CodeAnswerRequest> examenListCode;
    private EvaluationExamenType examenType;

    public static CreateSpecificationEvaluationPatientCommand fromRequest(CreateSpecificationEvaluationPatientRequest request) {
        return new CreateSpecificationEvaluationPatientCommand(
                request.getPatient(),
                request.getEvaluationId(),
                request.getExamenListCode(),
                request.getExamenType()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateSpecificationEvaluationPatientMessage(UUID.randomUUID());
    }
}
