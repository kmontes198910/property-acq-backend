package com.kynsoft.propertyacqcenter.application.command.propertyDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyDocumentService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdatePropertyDocumentCommandHandler implements ICommandHandler<UpdatePropertyDocumentCommand> {

    private final IPropertyDocumentService propertyDocumentService;
    private final IPropertyService propertyService;

    public UpdatePropertyDocumentCommandHandler(IPropertyDocumentService propertyDocumentService, IPropertyService propertyService) {
        this.propertyDocumentService = propertyDocumentService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdatePropertyDocumentCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        propertyDocumentService.update(PropertyDocumentDto.builder()
                .filePath(command.getFilePath())
                .id(command.getId())
                .fileName(command.getFileName())
                .property(propertyDto)
                .ownersContractRead(command.getOwnersContractRead())
                .assignmentOfContractRead(command.getAssignmentOfContractRead())
                .closingDate(command.getClosingDate())
                .platMapOrSurvey(command.getPlatMapOrSurvey())
                .earnestMoneyDeposit(command.getEarnestMoneyDeposit())
                .build());
    }
}
