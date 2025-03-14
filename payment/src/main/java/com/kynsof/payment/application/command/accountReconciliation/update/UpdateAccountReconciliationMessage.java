package com.kynsof.payment.application.command.accountReconciliation.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAccountReconciliationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ACCOUNT_RECONCILIATION";

    public UpdateAccountReconciliationMessage(UUID id) {
        this.id = id;
    }

}
