package com.kynsoft.cirugia.application.command.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateSurgeryMessage implements ICommandMessage {
    private final UUID id;

    public UpdateSurgeryMessage(UUID id) {
        this.id = id;
    }
}