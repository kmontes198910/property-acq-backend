package com.kynsof.patients.application.command.patients.patientsKeyCloack;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class patientsKeyCloakMessage implements ICommandMessage {


    private final String command = "UPDATE_PATIENT";

    public patientsKeyCloakMessage() {

    }

}
