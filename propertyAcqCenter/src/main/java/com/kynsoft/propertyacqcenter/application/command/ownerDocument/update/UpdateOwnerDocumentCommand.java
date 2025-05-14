package com.kynsoft.propertyacqcenter.application.command.ownerDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class UpdateOwnerDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String document;
    private UUID owner;

    public UpdateOwnerDocumentCommand(UUID id, String fileName, String document, UUID owner) {
        this.id = id;
        this.fileName = fileName;
        this.document = document;
        this.owner = owner;
    }

    public static UpdateOwnerDocumentCommand fromRequest(UpdateOwnerDocumentRequest request, UUID id){
        return new UpdateOwnerDocumentCommand(
                id,
                request.getFileName(),
                request.getDocument(),
                request.getOwner()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new UpdateOwnerDocumentMessage(id);
    }
}
