package com.kynsoft.propertyacqcenter.application.command.bankDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateBankDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_BANK_DOCUMENT";

    public UpdateBankDocumentMessage(UUID id) {
        this.id = id;
    }

}
