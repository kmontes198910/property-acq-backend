package com.kynsoft.propertyacqcenter.application.command.documentType.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import org.springframework.stereotype.Component;

@Component
public class CreateDocumentTypeCommandHandler implements ICommandHandler<CreateDocumentTypeCommand> {

    private final IDocumentTypeService documentTypeService;

    public CreateDocumentTypeCommandHandler(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Override
    public void handle(CreateDocumentTypeCommand command) {
        this.documentTypeService.validateCode(command.getCode(), command.getId());
        documentTypeService.create(new DocumentTypeDto(
                command.getId(),
                command.getCode(),
                command.getName()
        ));
    }
}
