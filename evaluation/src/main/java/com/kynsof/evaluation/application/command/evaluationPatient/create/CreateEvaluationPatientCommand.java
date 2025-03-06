package com.kynsof.evaluation.application.command.evaluationPatient.create;

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
    private UUID patient;
    private UUID evaluationId;
    private List<String> examenListCode;

    public static CreateEvaluationPatientCommand fromRequest(CreateEvaluationPatientRequest request) {
        return new CreateEvaluationPatientCommand(
                request.getPatient(),
                request.getEvaluationId(),
                request.getExamenListCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEvaluationPatientMessage(UUID.randomUUID());
    }
}
