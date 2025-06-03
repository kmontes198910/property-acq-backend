package com.kynsoft.propertyacqcenter.application.command.purchase.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePurchaseMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PURCHASE";

    public CreatePurchaseMessage(UUID id) {
        this.id = id;
    }

}
