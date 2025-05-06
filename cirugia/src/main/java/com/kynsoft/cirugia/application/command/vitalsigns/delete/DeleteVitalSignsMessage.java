package com.kynsoft.cirugia.application.command.vitalsigns.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteVitalSignsMessage implements ICommandMessage {
    private final UUID id;
    private final String message;

    public DeleteVitalSignsMessage(UUID id) {
        this.id = id;
        this.message = "Vital signs deleted successfully";
    }
}