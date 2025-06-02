package com.kynsoft.propertyacqcenter.application.command.legalEntity.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateLegalEntityMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_LEGAL_ENTITY";

    public CreateLegalEntityMessage(UUID id) {
        this.id = id;
    }

}
