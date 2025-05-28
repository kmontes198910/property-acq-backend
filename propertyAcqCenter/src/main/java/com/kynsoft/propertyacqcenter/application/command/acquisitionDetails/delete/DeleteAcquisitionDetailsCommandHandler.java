package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IAcquisitionDetailsService;
import org.springframework.stereotype.Component;

@Component
public class DeleteAcquisitionDetailsCommandHandler implements ICommandHandler<DeleteAcquisitionDetailsCommand> {

    private final IAcquisitionDetailsService acquisitionDetailsService;

    public DeleteAcquisitionDetailsCommandHandler(IAcquisitionDetailsService acquisitionDetailsService) {
        this.acquisitionDetailsService = acquisitionDetailsService;
    }

    @Override
    public void handle(DeleteAcquisitionDetailsCommand command) {

        this.acquisitionDetailsService.delete(command.getId());
    }

}
