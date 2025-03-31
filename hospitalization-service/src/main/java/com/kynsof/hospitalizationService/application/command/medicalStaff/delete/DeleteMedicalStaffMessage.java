package com.kynsof.hospitalizationService.application.command.medicalStaff.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteMedicalStaffMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_MEDICAL_STAFF";

    public DeleteMedicalStaffMessage(UUID id) {
        this.id = id;
    }

}
