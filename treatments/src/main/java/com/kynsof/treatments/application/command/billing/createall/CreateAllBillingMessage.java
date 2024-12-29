package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class CreateAllBillingMessage implements ICommandMessage {
    private final boolean result;
    private final String command = "CREATE_ALL_BILLINGS";

    public CreateAllBillingMessage(boolean result) {
        this.result = result;
    }

}
