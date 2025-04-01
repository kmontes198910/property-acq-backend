package com.kynsof.hospitalizationService.application.command.prescribedMedication.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePrescribedMedicationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PRESCRIPTION_MEDICATION";

    public UpdatePrescribedMedicationMessage(UUID id) {
        this.id = id;
    }

}
