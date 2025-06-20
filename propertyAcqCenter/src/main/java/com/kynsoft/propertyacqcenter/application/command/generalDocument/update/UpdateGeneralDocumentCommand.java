package com.kynsoft.propertyacqcenter.application.command.generalDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class UpdateGeneralDocumentCommand implements ICommand {

    private UUID id;
    private UUID documentType;
    private String fileName;
    private String filePath;

    public UpdateGeneralDocumentCommand(UUID id, UUID documentType, String fileName, String filePath) {
        this.id = id;
        this.documentType = documentType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static UpdateGeneralDocumentCommand fromRequest(UpdateGeneralDocumentRequest request, UUID id) {
        return new UpdateGeneralDocumentCommand(
                id,
                request.getDocumentType(),
                request.getFileName(),
                request.getFilePath()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateGeneralDocumentMessage(id);
    }
}
