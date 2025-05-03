package com.kynsoft.cirugia.application.command.preOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePreOperativeMessage implements ICommandMessage {
    private final UUID id;

    public CreatePreOperativeMessage(UUID id) {
        this.id = id;
    }
}