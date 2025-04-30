package com.kynsoft.cirugia.application.command.surgery.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateSurgeryMessage implements ICommandMessage {
    private final UUID id;

    public CreateSurgeryMessage(UUID id) {
        this.id = id;
    }
}