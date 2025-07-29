package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.buyerPersonalBankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateBuyerPersonalBankStatementRequestMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateBuyerPersonalBankStatementRequestMessage(UUID id) {
        this.id = id;
    }

}
