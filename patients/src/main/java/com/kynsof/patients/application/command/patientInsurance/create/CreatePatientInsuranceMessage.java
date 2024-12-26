package com.kynsof.patients.application.command.patientInsurance.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePatientInsuranceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PATIENT_INSURANCE";

    public CreatePatientInsuranceMessage(UUID id) {
        this.id = id;
    }

}
