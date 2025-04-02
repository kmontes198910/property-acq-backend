package com.kynsof.hospitalizationService.application.command.medicalPrescription.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateMedicalPrescriptionMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_MEDICAL_PRESCRIPTION";

    public CreateMedicalPrescriptionMessage(UUID id) {
        this.id = id;
    }

}
