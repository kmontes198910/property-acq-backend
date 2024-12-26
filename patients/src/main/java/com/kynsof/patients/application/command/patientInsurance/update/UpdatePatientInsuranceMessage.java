package com.kynsof.patients.application.command.patientInsurance.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class UpdatePatientInsuranceMessage implements ICommandMessage {


    private final String command = "UPDATE_ALLERGY";

    public UpdatePatientInsuranceMessage() {

    }

}
