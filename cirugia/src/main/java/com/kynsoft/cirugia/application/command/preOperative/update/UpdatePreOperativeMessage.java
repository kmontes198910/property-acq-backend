package com.kynsoft.cirugia.application.command.preOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePreOperativeMessage implements ICommandMessage {
    private final UUID id;

    public UpdatePreOperativeMessage(UUID id) {
        this.id = id;
    }
}