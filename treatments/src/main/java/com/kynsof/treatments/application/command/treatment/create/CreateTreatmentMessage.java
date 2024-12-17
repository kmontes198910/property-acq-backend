package com.kynsof.treatments.application.command.treatment.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateTreatmentMessage implements ICommandMessage {
    private final UUID id;
    private final String command = "CREATE_ALL_TREATMENT";

    public CreateTreatmentMessage(UUID id) {
        this.id = id;
    }

}
