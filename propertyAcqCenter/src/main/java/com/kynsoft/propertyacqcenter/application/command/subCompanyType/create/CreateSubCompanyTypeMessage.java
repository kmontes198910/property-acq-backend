package com.kynsoft.propertyacqcenter.application.command.subCompanyType.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateSubCompanyTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_CONSTRUCTION_TYPE";

    public CreateSubCompanyTypeMessage(UUID id) {
        this.id = id;
    }

}
