package com.kynsof.hospitalizationService.application.command.medicalPrescription.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteMedicalPrescriptionMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_MEDICAL_PRESCRIPTION";

    public DeleteMedicalPrescriptionMessage(UUID id) {
        this.id = id;
    }

}
