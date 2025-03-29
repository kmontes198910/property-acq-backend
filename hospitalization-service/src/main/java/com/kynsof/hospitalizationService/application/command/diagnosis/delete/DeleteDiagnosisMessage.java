package com.kynsof.hospitalizationService.application.command.diagnosis.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteDiagnosisMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_DIAGNOSIS";

    public DeleteDiagnosisMessage(UUID id) {
        this.id = id;
    }

}
