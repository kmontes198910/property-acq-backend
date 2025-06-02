package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCompanyContactMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_COMPANY_CONTACT";

    public CreateCompanyContactMessage(UUID id) {
        this.id = id;
    }

}
