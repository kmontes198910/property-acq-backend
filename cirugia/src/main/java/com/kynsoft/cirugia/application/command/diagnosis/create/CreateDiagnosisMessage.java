package com.kynsoft.cirugia.application.command.diagnosis.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateDiagnosisMessage implements ICommandMessage {
    private final UUID id;

    public CreateDiagnosisMessage(UUID id) {
        this.id = id;
    }
}