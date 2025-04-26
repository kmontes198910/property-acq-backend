package com.kynsoft.propertyacqcenter.application.command.bankAccount.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateBankAccountMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_BANK_ACCOUNT";

    public CreateBankAccountMessage(UUID id) {
        this.id = id;
    }

}
