package com.kynsoft.propertyacqcenter.application.command.ownerDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerShipLegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.LegalEntityNotNullException;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerDocumentService;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerShipLegalEntityService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UpdateOwnerDocumentCommandHandler implements ICommandHandler<UpdateOwnerDocumentCommand> {

    private final IOwnerShipLegalEntityService ownerShipLegalEntityService;
    private final IOwnerDocumentService ownerDocumentService;

    public UpdateOwnerDocumentCommandHandler(IOwnerShipLegalEntityService ownerShipLegalEntityService, IOwnerDocumentService ownerDocumentService) {
        this.ownerShipLegalEntityService = ownerShipLegalEntityService;
        this.ownerDocumentService = ownerDocumentService;
    }

    @Override
    public void handle(UpdateOwnerDocumentCommand command) {
        this.validateLegalEntityNotNull(command.getOwner());
        OwnerShipLegalEntityDto owner = this.ownerShipLegalEntityService.findById(command.getOwner());

        OwnerDocumentDto ownerDoc = OwnerDocumentDto.builder()
                .id(command.getId())
                .document(command.getDocument())
                .owner(owner)
                .fileName(command.getFileName())
                .build();

        this.ownerDocumentService.update(ownerDoc);
    }

    private void validateLegalEntityNotNull(UUID legalEntity) {
        if (legalEntity == null) {
            throw new LegalEntityNotNullException();
        }
    }
}
