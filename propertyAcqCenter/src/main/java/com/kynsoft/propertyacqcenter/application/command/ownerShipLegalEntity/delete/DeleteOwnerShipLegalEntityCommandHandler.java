package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerShipLegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class DeleteOwnerShipLegalEntityCommandHandler implements ICommandHandler<DeleteOwnerShipLegalEntityCommand> {

    private final IOwnerShipLegalEntityService ownerShipLegalEntityService;

    public DeleteOwnerShipLegalEntityCommandHandler(IOwnerShipLegalEntityService ownerShipLegalEntityService) {
        this.ownerShipLegalEntityService = ownerShipLegalEntityService;
    }

    @Override
    public void handle(DeleteOwnerShipLegalEntityCommand command) {
        this.ownerShipLegalEntityService.delete(command.getId());
    }
}
