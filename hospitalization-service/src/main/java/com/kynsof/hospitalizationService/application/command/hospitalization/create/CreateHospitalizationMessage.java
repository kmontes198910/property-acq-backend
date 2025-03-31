package com.kynsof.hospitalizationService.application.command.hospitalization.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateHospitalizationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_HOSPITALIZATION";

    public CreateHospitalizationMessage(UUID id) {
        this.id = id;
    }

}
