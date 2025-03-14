package com.kynsof.payment.application.command.accountReconciliation.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAccountReconciliationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_ACCOUNT_RECONCILIATION";

    public DeleteAccountReconciliationMessage(UUID id) {
        this.id = id;
    }

}
