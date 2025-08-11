package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateContractAddendumMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateContractAddendumMessage(UUID id) {
        this.id = id;
    }

}
