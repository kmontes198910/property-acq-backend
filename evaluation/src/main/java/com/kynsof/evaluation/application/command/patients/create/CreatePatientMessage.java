package com.kynsof.evaluation.application.command.patients.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePatientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PATIENT";

    public CreatePatientMessage(UUID id) {
        this.id = id;
    }

}
