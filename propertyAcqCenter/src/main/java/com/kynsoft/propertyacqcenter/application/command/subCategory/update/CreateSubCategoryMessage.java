package com.kynsoft.propertyacqcenter.application.command.subCategory.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateSubCategoryMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_CATEGORY";

    public CreateSubCategoryMessage(UUID id) {
        this.id = id;
    }

}
