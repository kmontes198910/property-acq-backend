package com.kynsof.hospitalizationService.application.command.prescribedMedication.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeletePrescribedMedicationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_PRESCRIPTION_MEDICATION";

    public DeletePrescribedMedicationMessage(UUID id) {
        this.id = id;
    }

}
