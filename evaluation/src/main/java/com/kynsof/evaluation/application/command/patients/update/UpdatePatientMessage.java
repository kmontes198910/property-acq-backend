package com.kynsof.evaluation.application.command.patients.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class UpdatePatientMessage implements ICommandMessage {


    private final String command = "UPDATE_PATIENT";

    public UpdatePatientMessage() {

    }

}
