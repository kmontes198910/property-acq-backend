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
    private UUID id;
    private final UUID patient;
    private final UUID evaluationId;
    private final List<CodeAnswerRequest> examenListCode;
    private final EvaluationExamenType examenType;

    public CreateSpecificationEvaluationPatientCommand(UUID patient, UUID evaluationId, List<CodeAnswerRequest> examenListCode, EvaluationExamenType examenType){
        this.patient = patient;
        this.evaluationId = evaluationId;
        this.examenListCode = examenListCode;
        this.examenType = examenType;
    }

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
        return new CreateSpecificationEvaluationPatientMessage(id);
    }
}
