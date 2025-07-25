package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateBankStatementRequestMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateBankStatementRequestMessage(UUID id) {
        this.id = id;
    }

}
