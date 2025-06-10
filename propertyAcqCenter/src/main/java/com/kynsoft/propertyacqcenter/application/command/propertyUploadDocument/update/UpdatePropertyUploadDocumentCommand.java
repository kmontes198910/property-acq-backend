package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyDocumentType;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyUploadDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String filePath;
    private String document;
    private String property;
    private PropertyDocumentType documentType;

    public UpdatePropertyUploadDocumentCommand(UUID id, String fileName, String document, String property, String filePath, PropertyDocumentType documentType) {
        this.id = id;
        this.fileName = fileName;
        this.document = document;
        this.property = property;
        this.filePath = filePath;
        this.documentType = documentType;
    }

    public static UpdatePropertyUploadDocumentCommand fromRequest(UpdatePropertyUploadDocumentRequest request, UUID id){
        return new UpdatePropertyUploadDocumentCommand(
                id,
                request.getFileName(),
                request.getDocument(),
                request.getProperty(),
                request.getFilePath(),
                request.getDocumentType()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyUploadDocumentMessage(id);
    }
}
