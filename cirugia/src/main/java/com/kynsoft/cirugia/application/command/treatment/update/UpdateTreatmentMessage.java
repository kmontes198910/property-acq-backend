package com.kynsoft.cirugia.application.command.treatment.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateTreatmentMessage implements ICommandMessage {
    private final UUID id;
    
    public UpdateTreatmentMessage(UUID id) {
        this.id = id;
    }
}