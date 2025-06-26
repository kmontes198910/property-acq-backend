package com.kynsoft.propertyacqcenter.application.command.subCategory.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSubCategoryMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_SUB_CATEGORY";

    public UpdateSubCategoryMessage(UUID id) {
        this.id = id;
    }

}
