package com.kynsof.evaluation.application.command.evaluationPatient.update;

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
public class UpdateEvaluationPatientCommand implements ICommand {

    private UUID id;
    private List<String> answers;


    public static UpdateEvaluationPatientCommand fromRequest(UpdateEvaluationPatientRequest request, UUID evaluationId) {
        return new UpdateEvaluationPatientCommand(
                evaluationId,
                request.getAnswers()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateEvaluationPatientMessage(id);
    }
}
