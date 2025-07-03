package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAdquisitionPropertySellerMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateAdquisitionPropertySellerMessage(UUID id) {
        this.id = id;
    }

}
