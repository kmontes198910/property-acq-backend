package com.kynsof.evaluation.application.command.evaluationPatient.createSpecification;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateSpecificationEvaluationPatientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION";

    public CreateSpecificationEvaluationPatientMessage(UUID id) {
        this.id = id;
    }

}
