package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAdquisitionPropertyMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_ADQUISITION";

    public DeleteAdquisitionPropertyMessage(UUID id) {
        this.id = id;
    }

}
