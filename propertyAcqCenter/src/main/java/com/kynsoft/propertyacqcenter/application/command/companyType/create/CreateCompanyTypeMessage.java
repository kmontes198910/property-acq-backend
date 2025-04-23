package com.kynsoft.propertyacqcenter.application.command.companyType.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCompanyTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_COMPANY_TYPE";

    public CreateCompanyTypeMessage(UUID id) {
        this.id = id;
    }

}
