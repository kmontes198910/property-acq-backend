package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteResponsibleMedicalStaffMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_RESPONSIBLE_MEDICAL_STAFF";

    public DeleteResponsibleMedicalStaffMessage(UUID id) {
        this.id = id;
    }

}
