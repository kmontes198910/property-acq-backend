package com.kynsoft.propertyacqcenter.application.command.mortgage.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import org.springframework.stereotype.Component;

@Component
public class DeleteMortgageCommandHandler implements ICommandHandler<DeleteMortgageCommand> {

    private final IMortgageService mortgageService;

    public DeleteMortgageCommandHandler(IMortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    @Override
    public void handle(DeleteMortgageCommand command) {
        this.mortgageService.delete(command.getId());
    }
}
