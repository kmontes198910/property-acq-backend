package com.kynsoft.cirugia.application.command.operatingroom.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateOperatingRoomMessage implements ICommandMessage {
    private final UUID id;

    public CreateOperatingRoomMessage(UUID id) {
        this.id = id;
    }
}