package com.kynsof.hospitalizationService.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDiagnosisMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_DIAGNOSIS";

    public UpdateDiagnosisMessage(UUID id) {
        this.id = id;
    }

}
