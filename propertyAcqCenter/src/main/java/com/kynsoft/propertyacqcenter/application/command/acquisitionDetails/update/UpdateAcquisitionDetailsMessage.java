package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAcquisitionDetailsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ACQUISITION_DETAILS";

    public UpdateAcquisitionDetailsMessage(UUID id) {
        this.id = id;
    }

}
