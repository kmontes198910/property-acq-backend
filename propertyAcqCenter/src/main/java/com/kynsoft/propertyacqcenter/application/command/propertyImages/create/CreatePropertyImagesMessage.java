package com.kynsoft.propertyacqcenter.application.command.propertyImages.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePropertyImagesMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PROPERTY_IMAGES";

    public CreatePropertyImagesMessage(UUID id) {
        this.id = id;
    }

}
