package com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSpecificationEvaluationPatientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION";

    public UpdateSpecificationEvaluationPatientMessage(UUID id) {
        this.id = id;
    }

}
