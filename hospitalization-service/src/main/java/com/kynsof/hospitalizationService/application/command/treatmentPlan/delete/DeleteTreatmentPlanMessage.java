package com.kynsof.hospitalizationService.application.command.treatmentPlan.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteTreatmentPlanMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_TREATMENT_PLAN";

    public DeleteTreatmentPlanMessage(UUID id) {
        this.id = id;
    }

}
