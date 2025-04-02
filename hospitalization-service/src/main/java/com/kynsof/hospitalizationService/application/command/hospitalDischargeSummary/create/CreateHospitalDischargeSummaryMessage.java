package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateHospitalDischargeSummaryMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_HOSPITAL_DISCHARGE_SUMMARY";

    public CreateHospitalDischargeSummaryMessage(UUID id) {
        this.id = id;
    }

}
