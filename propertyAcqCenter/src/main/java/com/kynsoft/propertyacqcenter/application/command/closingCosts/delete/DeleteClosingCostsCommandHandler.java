package com.kynsoft.propertyacqcenter.application.command.closingCosts.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IClosingCostsService;
import org.springframework.stereotype.Component;

@Component
public class DeleteClosingCostsCommandHandler implements ICommandHandler<DeleteClosingCostsCommand> {

    private final IClosingCostsService closingCostsService;

    public DeleteClosingCostsCommandHandler(IClosingCostsService closingCostsService) {
        this.closingCostsService = closingCostsService;
    }

    @Override
    public void handle(DeleteClosingCostsCommand command) {

        closingCostsService.delete(command.getId());
    }

}
