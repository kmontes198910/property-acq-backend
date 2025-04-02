package com.kynsof.hospitalizationService.application.command.bed.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateBedMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_BED";

    public CreateBedMessage(UUID id) {
        this.id = id;
    }

}
