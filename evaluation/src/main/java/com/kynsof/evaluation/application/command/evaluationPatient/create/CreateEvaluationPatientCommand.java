package com.kynsof.evaluation.application.command.evaluationPatient.create;

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
public class CreateEvaluationPatientCommand implements ICommand {
    private UUID id;
    private final UUID patient;
    private final UUID evaluationId;
    private final List<String> examenListCode;
    private final EvaluationExamenType examenType;

    public CreateEvaluationPatientCommand(UUID patient, UUID evaluationId, List<String> examenListCode, EvaluationExamenType examenType){

        this.patient = patient;
        this.evaluationId = evaluationId;
        this.examenListCode = examenListCode;
        this.examenType = examenType;
    }

    public static CreateEvaluationPatientCommand fromRequest(CreateEvaluationPatientRequest request) {
        return new CreateEvaluationPatientCommand(
                request.getPatient(),
                request.getEvaluationId(),
                request.getExamenListCode(),
                request.getExamenType()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEvaluationPatientMessage(id);
    }
}
