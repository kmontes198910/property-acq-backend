package com.kynsoft.propertyacqcenter.application.command.propertyDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyDocumentService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreatePropertyDocumentCommandHandler implements ICommandHandler<CreatePropertyDocumentCommand> {

    private final IPropertyDocumentService propertyDocumentService;
    private final IPropertyService propertyService;

    public CreatePropertyDocumentCommandHandler(IPropertyDocumentService propertyDocumentService, IPropertyService propertyService) {
        this.propertyDocumentService = propertyDocumentService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreatePropertyDocumentCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        propertyDocumentService.create(PropertyDocumentDto.builder()
                .filePath(command.getFilePath())
                .id(command.getId())
                .fileName(command.getFileName())
                .property(propertyDto)
                .build());
    }
}
