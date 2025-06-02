package com.kynsoft.propertyacqcenter.application.command.companyAddress.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCompanyAddressMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_COMPANY_ADDRESS";

    public CreateCompanyAddressMessage(UUID id) {
        this.id = id;
    }

}
