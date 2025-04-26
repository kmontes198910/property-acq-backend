package com.kynsoft.propertyacqcenter.application.command.bankAccount.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateBankAccountMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_BANK_ACCOUNT";

    public UpdateBankAccountMessage(UUID id) {
        this.id = id;
    }

}
