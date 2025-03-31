package com.kynsof.hospitalizationService.application.command.medicalStaff.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateMedicalStaffMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_MEDICAL_STAFF";

    public UpdateMedicalStaffMessage(UUID id) {
        this.id = id;
    }

}
