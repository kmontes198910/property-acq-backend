package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerPersonalBankStatements;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSellerPersonalBankStatementsRequestMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateSellerPersonalBankStatementsRequestMessage(UUID id) {
        this.id = id;
    }

}
