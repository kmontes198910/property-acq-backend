package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAdquisitionPropertyMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateAdquisitionPropertyMessage(UUID id) {
        this.id = id;
    }

}
