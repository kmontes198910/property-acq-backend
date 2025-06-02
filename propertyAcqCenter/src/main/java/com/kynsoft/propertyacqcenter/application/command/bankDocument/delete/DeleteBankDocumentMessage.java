package com.kynsoft.propertyacqcenter.application.command.bankDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteBankDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_BANK_DOCUMENT";

    public DeleteBankDocumentMessage(UUID id) {
        this.id = id;
    }

}
