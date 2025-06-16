package com.kynsoft.propertyacqcenter.application.command.documentType.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDocumentTypeCommand implements ICommand {

    private UUID id;
    private String code;
    private String name;

    public CreateDocumentTypeCommand(String code, String name) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.name = name;
    }

    public static CreateDocumentTypeCommand fromRequest(CreateDocumentTypeRequest request) {
        return new CreateDocumentTypeCommand(
                request.getCode(),
                request.getName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateDocumentTypeMessage(id);
    }
}
