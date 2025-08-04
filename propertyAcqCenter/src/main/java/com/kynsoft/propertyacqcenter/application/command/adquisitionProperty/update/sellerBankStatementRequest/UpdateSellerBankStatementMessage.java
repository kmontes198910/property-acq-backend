package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSellerBankStatementMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateSellerBankStatementMessage(UUID id) {
        this.id = id;
    }

}
