package com.kynsoft.propertyacqcenter.application.command.propertyImages.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePropertyImagesMessage implements ICommandMessage {
    private final String command = "DELETE_PROPERTY_IMAGES";

    private final UUID id;

}
