package com.kynsof.hospitalizationService.application.command.hospitalization.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteHospitalizationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_DIAGNOSIS";

    public DeleteHospitalizationMessage(UUID id) {
        this.id = id;
    }

}
