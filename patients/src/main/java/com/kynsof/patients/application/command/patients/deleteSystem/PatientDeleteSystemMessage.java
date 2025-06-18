package com.kynsof.patients.application.command.patients.deleteSystem;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;


@Getter
public class PatientDeleteSystemMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_PATIENT_SYSTEM";

    public PatientDeleteSystemMessage(UUID id) {
        this.id = id;
    }

}
