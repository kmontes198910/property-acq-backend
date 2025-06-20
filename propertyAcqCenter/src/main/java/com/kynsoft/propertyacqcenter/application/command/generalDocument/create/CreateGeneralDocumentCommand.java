package com.kynsoft.propertyacqcenter.application.command.generalDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateGeneralDocumentCommand implements ICommand {

    private UUID id;
    private UUID documentType;
    private String fileName;
    private String filePath;

    public CreateGeneralDocumentCommand(UUID documentType, String fileName, String filePath) {
        this.id = UUID.randomUUID();
        this.documentType = documentType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static CreateGeneralDocumentCommand fromRequest(CreateGeneralDocumentRequest request) {
        return new CreateGeneralDocumentCommand(
                request.getDocumentType(),
                request.getFileName(),
                request.getFilePath()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateGeneralDocumentMessage(id);
    }
}
