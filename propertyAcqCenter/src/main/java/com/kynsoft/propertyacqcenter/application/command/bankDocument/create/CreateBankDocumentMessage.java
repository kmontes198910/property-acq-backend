package com.kynsoft.propertyacqcenter.application.command.bankDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateBankDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_BANK_DOCUMENT";

    public CreateBankDocumentMessage(UUID id) {
        this.id = id;
    }

}
