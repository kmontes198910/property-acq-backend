package com.kynsof.evaluation.application.command.patients.delete;



import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PatientDeleteMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_PATIENT";

    public PatientDeleteMessage(UUID id) {
        this.id = id;
    }

}
