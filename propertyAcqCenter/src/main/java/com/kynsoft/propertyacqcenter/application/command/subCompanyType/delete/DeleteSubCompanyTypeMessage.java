package com.kynsoft.propertyacqcenter.application.command.subCompanyType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteSubCompanyTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_CONSTRUCTION_TYPE";

    public DeleteSubCompanyTypeMessage(UUID id) {
        this.id = id;
    }

}
