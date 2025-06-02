package com.kynsoft.cirugia.application.command.vitalsigns.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateVitalSignsMessage implements ICommandMessage {
    private final UUID id;
    private final String message;

    public CreateVitalSignsMessage(UUID id) {
        this.id = id;
        this.message = "Vital signs recorded successfully";
    }
}