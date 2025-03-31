package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateResponsibleMedicalStaffMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_RESPONSIBLE_MEDICAL_STAFF";

    public UpdateResponsibleMedicalStaffMessage(UUID id) {
        this.id = id;
    }

}
