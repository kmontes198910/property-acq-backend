package com.kynsoft.propertyacqcenter.application.command.companyType.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateCompanyTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_COMPANY_TYPE";

    public UpdateCompanyTypeMessage(UUID id) {
        this.id = id;
    }

}
