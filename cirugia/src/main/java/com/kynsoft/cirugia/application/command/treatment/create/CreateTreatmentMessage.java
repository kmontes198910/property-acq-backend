package com.kynsoft.cirugia.application.command.treatment.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateTreatmentMessage implements ICommandMessage {
    private final UUID id;
    
    public CreateTreatmentMessage(UUID id) {
        this.id = id;
    }
}