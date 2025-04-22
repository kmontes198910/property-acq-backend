package com.kynsoft.notification.application.command.file.saveFileS3;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SaveFileS3Message  implements ICommandMessage {
    private final UUID id;
    private final String viewUrl;
    private final Long fileSize;


    
    public SaveFileS3Message(UUID id, String viewUrl, Long fileSize) {
        this.id = id;

        this.viewUrl = viewUrl;
        this.fileSize = fileSize;
    }
}
