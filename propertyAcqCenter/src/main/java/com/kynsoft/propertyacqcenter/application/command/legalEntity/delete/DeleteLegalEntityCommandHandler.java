package com.kynsoft.propertyacqcenter.application.command.legalEntity.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class DeleteLegalEntityCommandHandler implements ICommandHandler<DeleteLegalEntityCommand> {

    private final ILegalEntityService legalEntityService;

    public DeleteLegalEntityCommandHandler(ILegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(DeleteLegalEntityCommand command) {
        this.legalEntityService.delete(command.getId());
    }
}
