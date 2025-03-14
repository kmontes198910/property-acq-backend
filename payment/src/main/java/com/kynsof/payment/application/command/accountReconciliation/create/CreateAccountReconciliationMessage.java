package com.kynsof.payment.application.command.accountReconciliation.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAccountReconciliationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ACCOUNT_RECONCILIATION";

    public CreateAccountReconciliationMessage(UUID id) {
        this.id = id;
    }

}
