package com.kynsof.hospitalizationService.application.command.medicalPrescription.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateMedicalPrescriptionMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_MEDICAL_PRESCRIPTION";

    public UpdateMedicalPrescriptionMessage(UUID id) {
        this.id = id;
    }

}
