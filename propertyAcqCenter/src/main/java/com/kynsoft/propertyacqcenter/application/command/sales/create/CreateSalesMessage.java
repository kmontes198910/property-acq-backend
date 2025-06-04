package com.kynsoft.propertyacqcenter.application.command.sales.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateSalesMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PURCHASE";

    public CreateSalesMessage(UUID id) {
        this.id = id;
    }

}
