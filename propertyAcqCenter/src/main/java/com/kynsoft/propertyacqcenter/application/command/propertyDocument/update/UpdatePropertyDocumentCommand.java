package com.kynsoft.propertyacqcenter.application.command.propertyDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePropertyDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String property;
    private String filePath;

    public UpdatePropertyDocumentCommand(UUID id, String filePath, String property, String fileName) {
        this.id = id;
        this.filePath = filePath;
        this.property = property;
        this.fileName = fileName;
    }

    public static UpdatePropertyDocumentCommand fromRequest(UpdatePropertyDocumentRequest request, UUID id) {
        return new UpdatePropertyDocumentCommand(
                id,
                request.getFilePath(),
                request.getProperty(),
                request.getFileName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyDocumentMessage(id);
    }
}
