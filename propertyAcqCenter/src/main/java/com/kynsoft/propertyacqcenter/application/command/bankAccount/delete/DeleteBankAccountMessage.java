package com.kynsoft.propertyacqcenter.application.command.bankAccount.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteBankAccountMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_BANK_ACCOUNT";

    public DeleteBankAccountMessage(UUID id) {
        this.id = id;
    }

}
