package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerShipLegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.LegalEntityNotNullException;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerShipLegalEntityService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UpdateOwnerShipLegalEntityCommandHandler implements ICommandHandler<UpdateOwnerShipLegalEntityCommand> {

    private final IOwnerShipLegalEntityService ownerShipLegalEntityService;
    private final ILegalEntityService legalEntityService;

    public UpdateOwnerShipLegalEntityCommandHandler(IOwnerShipLegalEntityService ownerShipLegalEntityService, ILegalEntityService legalEntityService) {
        this.ownerShipLegalEntityService = ownerShipLegalEntityService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdateOwnerShipLegalEntityCommand command) {
        this.validateLegalEntityNotNull(command.getLegalEntity());
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());

        OwnerShipLegalEntityDto ownerShipLegalEntityDto = OwnerShipLegalEntityDto.builder()
                .id(command.getId())
                .name(command.getName())
                .ownershipPercentage(command.getOwnershipPercentage())
                .legalEntity(legalEntityDto)
                .build();

        this.ownerShipLegalEntityService.update(ownerShipLegalEntityDto);
    }

    private void validateLegalEntityNotNull(UUID legalEntity) {
        if (legalEntity == null) {
            throw new LegalEntityNotNullException();
        }
    }
}
