package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceBrokerService;
import org.springframework.stereotype.Component;

@Component
public class DeleteInsuranceBrokerCommandHandler implements ICommandHandler<DeleteInsuranceBrokerCommand> {

    private final IInsuranceBrokerService insuranceBrokerService;

    public DeleteInsuranceBrokerCommandHandler(IInsuranceBrokerService insuranceBrokerService) {
        this.insuranceBrokerService = insuranceBrokerService;
    }

    @Override
    public void handle(DeleteInsuranceBrokerCommand command) {
        this.insuranceBrokerService.delete(command.getId());
    }
}
