package com.kynsoft.propertyacqcenter.application.command.business.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;

@Component
public class DeleteBusinessCommandHandler implements ICommandHandler<DeleteBusinessCommand> {

    private final IBusinessService businessService;

    public DeleteBusinessCommandHandler(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public void handle(DeleteBusinessCommand command) {

        businessService.delete(command.getId());
    }

}
