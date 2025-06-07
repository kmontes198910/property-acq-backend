package com.kynsoft.settings.application.command.recoveryroom.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteRecoveryRoomMessage implements ICommandMessage {
    private UUID id;

    public DeleteRecoveryRoomMessage(UUID id) {
        this.id = id;
    }
}