package com.kynsoft.propertyacqcenter.application.command.ownerDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateOwnerDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String document;
    private UUID owner;

    public CreateOwnerDocumentCommand(String fileName, String document, UUID owner) {
        this.id = UUID.randomUUID();
        this.fileName = fileName;
        this.document = document;
        this.owner = owner;
    }

    public static CreateOwnerDocumentCommand fromRequest(CreateOwnerDocumentRequest request){
        return new CreateOwnerDocumentCommand(
                request.getFileName(),
                request.getDocument(),
                request.getOwner()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateOwnerDocumentMessage(id);
    }
}
