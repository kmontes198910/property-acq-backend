package com.kynsoft.propertyacqcenter.application.command.companyAddress.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateCompanyAddressMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_COMPANY_ADDRESS";

    public UpdateCompanyAddressMessage(UUID id) {
        this.id = id;
    }

}
