package com.kynsoft.notification.application.command.file.saveFileS3;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SaveFileS3Message  implements ICommandMessage {
    private final UUID id;
    private final String url;
    private final String viewUrl;
    private final Long fileSize;

    public SaveFileS3Message(UUID id, String url, String viewUrl) {
        this.id = id;
        this.url = url;
        this.viewUrl = viewUrl;
        this.fileSize = null;
    }
    
    public SaveFileS3Message(UUID id, String url, String viewUrl, Long fileSize) {
        this.id = id;
        this.url = url;
        this.viewUrl = viewUrl;
        this.fileSize = fileSize;
    }
}
