package com.kynsof.hospitalizationService.application.command.vitalSigns.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateVitalSignsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_VITAL_SIGNS";

    public CreateVitalSignsMessage(UUID id) {
        this.id = id;
    }

}
