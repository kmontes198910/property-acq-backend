package com.kynsof.hospitalizationService.application.command.bed.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteBedMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_BED";

    public DeleteBedMessage(UUID id) {
        this.id = id;
    }

}
