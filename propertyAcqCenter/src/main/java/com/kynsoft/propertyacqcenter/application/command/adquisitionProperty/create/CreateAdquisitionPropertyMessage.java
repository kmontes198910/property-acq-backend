package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAdquisitionPropertyMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ADQUISITION";

    public CreateAdquisitionPropertyMessage(UUID id) {
        this.id = id;
    }

}
