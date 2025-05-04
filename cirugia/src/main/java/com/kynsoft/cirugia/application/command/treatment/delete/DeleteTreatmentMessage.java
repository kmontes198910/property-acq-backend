package com.kynsoft.cirugia.application.command.treatment.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteTreatmentMessage implements ICommandMessage {
    private final UUID id;
    
    public DeleteTreatmentMessage(UUID id) {
        this.id = id;
    }
}