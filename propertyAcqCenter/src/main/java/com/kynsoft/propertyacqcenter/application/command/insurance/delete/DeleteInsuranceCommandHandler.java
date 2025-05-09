package com.kynsoft.propertyacqcenter.application.command.insurance.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceService;
import org.springframework.stereotype.Component;

@Component
public class DeleteInsuranceCommandHandler implements ICommandHandler<DeleteInsuranceCommand> {

    private final IInsuranceService insuranceService;

    public DeleteInsuranceCommandHandler(IInsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Override
    public void handle(DeleteInsuranceCommand command) {
        this.insuranceService.delete(command.getId());
    }
}
