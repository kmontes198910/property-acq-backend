package com.kynsoft.propertyacqcenter.application.command.generalDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;

import jakarta.transaction.Transactional;

import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.domain.services.IGeneralDocumentService;

import org.springframework.stereotype.Component;

@Component
public class UpdateGeneralDocumentCommandHandler implements ICommandHandler<UpdateGeneralDocumentCommand> {

    private final IGeneralDocumentService generalDocumentService;
    private final IDocumentTypeService documentTypeService;

    public UpdateGeneralDocumentCommandHandler(IGeneralDocumentService generalDocumentService, IDocumentTypeService documentTypeService) {
        this.generalDocumentService = generalDocumentService;
        this.documentTypeService = documentTypeService;
    }

    @Override
    @Transactional
    public void handle(UpdateGeneralDocumentCommand command) {
        DocumentTypeDto documentType = command.getDocumentType() != null ? this.documentTypeService.findById(command.getDocumentType()) : null;
        this.generalDocumentService.update(GeneralDocumentDto
                .builder()
                .id(command.getId())
                .documentType(documentType)
                .fileName(command.getFileName())
                .filePath(command.getFilePath())
                .build());
    }

}
