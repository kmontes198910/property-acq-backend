package com.kynsoft.propertyacqcenter.application.command.companyContact.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteCompanyContactMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_COMPANY_CONTACT";

    public DeleteCompanyContactMessage(UUID id) {
        this.id = id;
    }

}
