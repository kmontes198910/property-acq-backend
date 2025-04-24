package com.kynsoft.propertyacqcenter.application.command.constructionType.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateConstructionTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_CONSTRUCTION_TYPE";

    public UpdateConstructionTypeMessage(UUID id) {
        this.id = id;
    }

}
