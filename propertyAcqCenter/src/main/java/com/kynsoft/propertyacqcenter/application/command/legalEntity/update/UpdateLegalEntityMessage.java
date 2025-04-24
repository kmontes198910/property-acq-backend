package com.kynsoft.propertyacqcenter.application.command.legalEntity.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateLegalEntityMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_LEGAL_ENTITY";

    public UpdateLegalEntityMessage(UUID id) {
        this.id = id;
    }

}
