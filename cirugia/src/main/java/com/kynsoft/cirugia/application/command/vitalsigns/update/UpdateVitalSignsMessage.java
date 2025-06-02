package com.kynsoft.cirugia.application.command.vitalsigns.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateVitalSignsMessage implements ICommandMessage {
    private final UUID id;
    private final String message;

    public UpdateVitalSignsMessage(UUID id) {
        this.id = id;
        this.message = "Vital signs updated successfully";
    }
}