package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateResponsibleMedicalStaffMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_RESPONSIBLE_MEDICAL_STAFF";

    public CreateResponsibleMedicalStaffMessage(UUID id) {
        this.id = id;
    }

}
