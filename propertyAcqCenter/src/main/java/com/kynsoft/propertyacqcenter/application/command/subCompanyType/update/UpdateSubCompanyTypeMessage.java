package com.kynsoft.propertyacqcenter.application.command.subCompanyType.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSubCompanyTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_CONSTRUCTION_TYPE";

    public UpdateSubCompanyTypeMessage(UUID id) {
        this.id = id;
    }

}
