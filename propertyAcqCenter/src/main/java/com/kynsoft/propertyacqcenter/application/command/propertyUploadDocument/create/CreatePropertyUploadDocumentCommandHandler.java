package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyUploadDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.propertyUploadDocument.PropertyNotNullException;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyUploadDocumentService;
import org.springframework.stereotype.Component;

@Component
public class CreatePropertyUploadDocumentCommandHandler implements ICommandHandler<CreatePropertyUploadDocumentCommand> {

    private final IPropertyUploadDocumentService propertyUploadDocumentService;
    private final IPropertyService propertyService;

    public CreatePropertyUploadDocumentCommandHandler(IPropertyUploadDocumentService propertyUploadDocumentService, IPropertyService propertyService) {
        this.propertyUploadDocumentService = propertyUploadDocumentService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreatePropertyUploadDocumentCommand command) {
        this.validatePropertyNotNull(command.getProperty());
        PropertyDto property = this.propertyService.getById(command.getProperty());

        PropertyUploadDocumentDto doc = PropertyUploadDocumentDto.builder()
                .id(command.getId())
                .document(command.getDocument())
                .property(property)
                .fileName(command.getFileName())
                .build();

        this.propertyUploadDocumentService.create(doc);
    }

    private void validatePropertyNotNull(String property) {
        if (property == null || property.equals("")) {
            throw new PropertyNotNullException();
        }
    }
}
