package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateHospitalDischargeSummaryMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_HOSPITAL_DISCHARGE_SUMMARY";

    public UpdateHospitalDischargeSummaryMessage(UUID id) {
        this.id = id;
    }

}
