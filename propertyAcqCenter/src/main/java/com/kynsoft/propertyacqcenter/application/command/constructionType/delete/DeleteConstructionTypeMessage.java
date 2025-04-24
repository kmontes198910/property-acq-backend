package com.kynsoft.propertyacqcenter.application.command.constructionType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteConstructionTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_CONSTRUCTION_TYPE";

    public DeleteConstructionTypeMessage(UUID id) {
        this.id = id;
    }

}
