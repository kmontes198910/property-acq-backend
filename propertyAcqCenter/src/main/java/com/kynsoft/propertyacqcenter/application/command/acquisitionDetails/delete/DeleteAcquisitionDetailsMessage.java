package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAcquisitionDetailsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_ACQUISITION_DETAILS";

    public DeleteAcquisitionDetailsMessage(UUID id) {
        this.id = id;
    }

}
