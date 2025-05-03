package com.kynsoft.cirugia.application.command.postOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePostOperativeMessage implements ICommandMessage {
    private final UUID id;

    public CreatePostOperativeMessage(UUID id) {
        this.id = id;
    }
}