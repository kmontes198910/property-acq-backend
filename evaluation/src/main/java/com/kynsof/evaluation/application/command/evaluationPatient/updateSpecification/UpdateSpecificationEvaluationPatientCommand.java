package com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification;

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
public class UpdateSpecificationEvaluationPatientCommand implements ICommand {
    private UUID id;
    private List<CodeAnswerUpdateRequest> examenListCode;


    public static UpdateSpecificationEvaluationPatientCommand fromRequest(UpdateSpecificationEvaluationPatientRequest request,UUID id) {
        return new UpdateSpecificationEvaluationPatientCommand(
               id,
                request.getExamenListCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSpecificationEvaluationPatientMessage(UUID.randomUUID());
    }
}
