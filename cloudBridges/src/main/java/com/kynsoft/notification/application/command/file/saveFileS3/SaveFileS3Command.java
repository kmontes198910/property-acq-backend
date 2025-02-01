package com.kynsoft.notification.application.command.file.saveFileS3;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
public class SaveFileS3Command implements ICommand {
    private final MultipartFile multipartFile;
    private UUID fileId;
    private String url;
    private final String fileName;
    private final String objectId;
    private final String folderPath;

    public SaveFileS3Command(MultipartFile multipartFile, String fileName, String objectId, String folderPath) {
        this.multipartFile = multipartFile;
        this.fileName = fileName;
        this.objectId = objectId;
        this.folderPath = folderPath;
    }



    @Override
    public ICommandMessage getMessage() {
        return new SaveFileS3Message(url,fileId);
    }
}
