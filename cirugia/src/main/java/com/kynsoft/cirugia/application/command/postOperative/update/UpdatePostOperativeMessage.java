package com.kynsoft.cirugia.application.command.postOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePostOperativeMessage implements ICommandMessage {
    private final UUID id;

    public UpdatePostOperativeMessage(UUID id) {
        this.id = id;
    }
}