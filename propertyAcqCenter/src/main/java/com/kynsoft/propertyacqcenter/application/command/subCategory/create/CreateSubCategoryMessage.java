package com.kynsoft.propertyacqcenter.application.command.subCategory.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateSubCategoryMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_SUB_CATEGORY";

    public CreateSubCategoryMessage(UUID id) {
        this.id = id;
    }

}
