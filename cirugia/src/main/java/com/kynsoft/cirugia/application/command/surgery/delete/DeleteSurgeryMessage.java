package com.kynsoft.cirugia.application.command.surgery.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteSurgeryMessage implements ICommandMessage {
    private final UUID id;

    public DeleteSurgeryMessage(UUID id) {
        this.id = id;
    }
}