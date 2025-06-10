package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyDocumentType;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreatePropertyUploadDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String filePath;
    private String document;
    private String property;
    private PropertyDocumentType documentType;

    public CreatePropertyUploadDocumentCommand(String fileName, String document, String property, String filePath, PropertyDocumentType documentType) {
        this.id = UUID.randomUUID();
        this.fileName = fileName;
        this.document = document;
        this.property = property;
        this.filePath = filePath;
        this.documentType = documentType;
    }

    public static CreatePropertyUploadDocumentCommand fromRequest(CreatePropertyUploadDocumentRequest request){
        return new CreatePropertyUploadDocumentCommand(
                request.getFileName(),
                request.getDocument(),
                request.getProperty(),
                request.getFilePath(),
                request.getDocumentType()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreatePropertyUploadDocumentMessage(id);
    }
}
