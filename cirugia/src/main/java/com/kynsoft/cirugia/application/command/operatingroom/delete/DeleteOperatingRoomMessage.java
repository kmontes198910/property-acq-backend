package com.kynsoft.cirugia.application.command.operatingroom.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteOperatingRoomMessage implements ICommandMessage {
    private final UUID id;

    public DeleteOperatingRoomMessage(UUID id) {
        this.id = id;
    }
}