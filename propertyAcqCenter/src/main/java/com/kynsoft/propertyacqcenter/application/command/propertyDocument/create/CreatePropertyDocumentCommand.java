package com.kynsoft.propertyacqcenter.application.command.propertyDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePropertyDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String property;
    private String filePath;

    public CreatePropertyDocumentCommand(String filePath, String property, String fileName) {
        this.id = UUID.randomUUID();
        this.filePath = filePath;
        this.property = property;
        this.fileName = fileName;
    }

    public static CreatePropertyDocumentCommand fromRequest(CreatePropertyDocumentRequest request) {
        return new CreatePropertyDocumentCommand(
                request.getFilePath(),
                request.getProperty(),
                request.getFileName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePropertyDocumentMessage(id);
    }
}
