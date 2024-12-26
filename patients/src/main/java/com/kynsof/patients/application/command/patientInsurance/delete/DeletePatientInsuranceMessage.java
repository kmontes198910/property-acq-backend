package com.kynsof.patients.application.command.patientInsurance.delete;



import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeletePatientInsuranceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_PATIENT";

    public DeletePatientInsuranceMessage(UUID id) {
        this.id = id;
    }

}
