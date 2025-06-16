package com.kynsoft.propertyacqcenter.application.command.documentType.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import org.springframework.stereotype.Component;

@Component
public class UpdateDocumentTypeCommandHandler implements ICommandHandler<UpdateDocumentTypeCommand> {

    private final IDocumentTypeService documentTypeService;

    public UpdateDocumentTypeCommandHandler(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Override
    public void handle(UpdateDocumentTypeCommand command) {
        this.documentTypeService.validateCode(command.getCode(), command.getId());
        documentTypeService.update(new DocumentTypeDto(
                command.getId(),
                command.getCode(),
                command.getName()
        ));
    }
}
