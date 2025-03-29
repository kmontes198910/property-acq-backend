package com.kynsof.hospitalizationService.application.command.treatmentPlan.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateTreatmentPlanMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_TREATMENT_PLAN";

    public CreateTreatmentPlanMessage(UUID id) {
        this.id = id;
    }

}
