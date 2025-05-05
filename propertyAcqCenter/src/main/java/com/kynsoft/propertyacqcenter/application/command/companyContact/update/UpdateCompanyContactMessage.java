package com.kynsoft.propertyacqcenter.application.command.companyContact.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateCompanyContactMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_COMPANY_CONTACT";

    public UpdateCompanyContactMessage(UUID id) {
        this.id = id;
    }

}
