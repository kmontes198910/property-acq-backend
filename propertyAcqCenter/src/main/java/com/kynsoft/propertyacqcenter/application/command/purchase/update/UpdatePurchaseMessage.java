package com.kynsoft.propertyacqcenter.application.command.purchase.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePurchaseMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PURCHASE";

    public UpdatePurchaseMessage(UUID id) {
        this.id = id;
    }

}
