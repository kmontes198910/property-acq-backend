package com.kynsoft.propertyacqcenter.application.command.documentType.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDocumentTypeCommand implements ICommand {

    private UUID id;
    private String code;
    private String name;

    public UpdateDocumentTypeCommand(UUID id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public static UpdateDocumentTypeCommand fromRequest(UpdateDocumentTypeRequest request, UUID id) {
        return new UpdateDocumentTypeCommand(
                id,
                request.getCode(),
                request.getName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateDocumentTypeMessage(id);
    }
}
