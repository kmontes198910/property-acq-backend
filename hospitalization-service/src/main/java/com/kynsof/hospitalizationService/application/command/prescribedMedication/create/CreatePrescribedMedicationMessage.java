package com.kynsof.hospitalizationService.application.command.prescribedMedication.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePrescribedMedicationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PRESCRIPTION_MEDICATION";

    public CreatePrescribedMedicationMessage(UUID id) {
        this.id = id;
    }

}
