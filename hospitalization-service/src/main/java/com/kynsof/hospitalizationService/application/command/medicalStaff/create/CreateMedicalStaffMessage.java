package com.kynsof.hospitalizationService.application.command.medicalStaff.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateMedicalStaffMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_MEDICAL_STAFF";

    public CreateMedicalStaffMessage(UUID id) {
        this.id = id;
    }

}
