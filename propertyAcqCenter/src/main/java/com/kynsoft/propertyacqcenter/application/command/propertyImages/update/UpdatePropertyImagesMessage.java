package com.kynsoft.propertyacqcenter.application.command.propertyImages.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePropertyImagesMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PROPERTY_IMAGES";

    public UpdatePropertyImagesMessage(UUID id) {
        this.id = id;
    }

}
