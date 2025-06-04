package com.kynsoft.propertyacqcenter.application.command.sales.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSalesMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PURCHASE";

    public UpdateSalesMessage(UUID id) {
        this.id = id;
    }

}
