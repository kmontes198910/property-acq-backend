package com.kynsoft.propertyacqcenter.application.command.generalDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;

import jakarta.transaction.Transactional;

import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.domain.services.IGeneralDocumentService;

import org.springframework.stereotype.Component;

@Component
public class CreateGeneralDocumentCommandHandler implements ICommandHandler<CreateGeneralDocumentCommand> {

    private final IGeneralDocumentService generalDocumentService;
    private final IDocumentTypeService documentTypeService;
    private final IAdquisitionPropertyService adquisitionPropertyService;

    public CreateGeneralDocumentCommandHandler(IGeneralDocumentService generalDocumentService, 
                                               IDocumentTypeService documentTypeService,
                                               IAdquisitionPropertyService adquisitionPropertyService) {
        this.generalDocumentService = generalDocumentService;
        this.documentTypeService = documentTypeService;
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    @Transactional
    public void handle(CreateGeneralDocumentCommand command) {
        DocumentTypeDto documentType = command.getDocumentType() != null ? this.documentTypeService.findById(command.getDocumentType()) : null;
        AdquisitionPropertyDto adquisitionProperty = command.getAdquisitionProperty() != null ? this.adquisitionPropertyService.findById(command.getAdquisitionProperty()) : null;
        this.generalDocumentService.create(GeneralDocumentDto
                .builder()
                .id(command.getId())
                .documentType(documentType)
                .fileName(command.getFileName())
                .filePath(command.getFilePath())
                .adquisitionProperty(adquisitionProperty)
                .build());
    }

}
