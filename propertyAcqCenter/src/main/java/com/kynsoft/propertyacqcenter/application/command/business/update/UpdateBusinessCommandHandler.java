package com.kynsoft.propertyacqcenter.application.command.business.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;

@Component
public class UpdateBusinessCommandHandler implements ICommandHandler<UpdateBusinessCommand> {

    private final IBusinessService businessService;

    public UpdateBusinessCommandHandler(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public void handle(UpdateBusinessCommand command) {
        BusinessDto update = this.businessService.findById(command.getId());
        update.setName(command.getName());

        businessService.update(update);
    }
}
