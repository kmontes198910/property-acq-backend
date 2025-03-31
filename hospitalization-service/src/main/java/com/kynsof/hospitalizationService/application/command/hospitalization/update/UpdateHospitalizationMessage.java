package com.kynsof.hospitalizationService.application.command.hospitalization.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateHospitalizationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_HOSPITALIZATION";

    public UpdateHospitalizationMessage(UUID id) {
        this.id = id;
    }

}
