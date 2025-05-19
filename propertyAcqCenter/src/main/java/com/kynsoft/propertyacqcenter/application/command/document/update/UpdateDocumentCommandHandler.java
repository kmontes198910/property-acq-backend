package com.kynsoft.propertyacqcenter.application.command.document.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class UpdateDocumentCommandHandler implements ICommandHandler<UpdateDocumentCommand> {

    private final IDocumentService documentService;
    private final ILegalEntityService legalEntityService;

    public UpdateDocumentCommandHandler(IDocumentService documentService, ILegalEntityService legalEntityService) {
        this.documentService = documentService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdateDocumentCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        documentService.update(new DocumentDto(
                command.getId(), 
                legalEntityDto, 
                command.getFileName(), 
                command.getFilePath(), 
                command.getDocumentType(), 
                command.getDescription(), 
                command.getFileSize(), 
                command.getContentType(), 
                command.getExpirationDate(), 
                command.getIssuedBy(), 
                command.getIssuingDate(), 
                command.getDocumentStatus(), 
                command.getVerificationDate(), 
                command.getVerifiedBy(), 
                command.getDocumentNumber(), 
                command.getIsOriginal(), 
                command.getVersion(),
                command.getRenewalDate(), 
                command.getTags(), 
                command.getNotes(), 
                null, 
                null
        ));
    }
}
