package com.kynsoft.propertyacqcenter.application.command.companyType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteCompanyTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_COMPANY_TYPE";

    public DeleteCompanyTypeMessage(UUID id) {
        this.id = id;
    }

}
