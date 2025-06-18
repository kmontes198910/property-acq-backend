package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import org.springframework.stereotype.Component;

@Component
public class DeleteAdquisitionPropertyCommandHandler implements ICommandHandler<DeleteAdquisitionPropertyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public DeleteAdquisitionPropertyCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(DeleteAdquisitionPropertyCommand command) {

        adquisitionPropertyService.delete(command.getId());
    }

}
