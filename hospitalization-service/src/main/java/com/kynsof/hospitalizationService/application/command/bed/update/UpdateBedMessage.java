package com.kynsof.hospitalizationService.application.command.bed.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateBedMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_BED";

    public UpdateBedMessage(UUID id) {
        this.id = id;
    }

}
