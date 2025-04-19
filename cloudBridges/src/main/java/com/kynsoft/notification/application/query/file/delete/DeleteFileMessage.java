package com.kynsoft.notification.application.query.file.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteFileMessage implements ICommandMessage {

    private final boolean result;

    private final String command = "DELETE_FILE";

    public DeleteFileMessage(boolean result) {
        this.result = result;
    }

}
