package com.kynsoft.propertyacqcenter.application.command.property.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class DeletePropertyCommandHandler implements ICommandHandler<DeletePropertyCommand> {

    private final IPropertyService propertyService;

    public DeletePropertyCommandHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public void handle(DeletePropertyCommand command) {
        this.propertyService.delete(command.getId());
    }
}
