package com.kynsoft.cirugia.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDiagnosisMessage implements ICommandMessage {
    private final UUID id;

    public UpdateDiagnosisMessage(UUID id) {
        this.id = id;
    }
}