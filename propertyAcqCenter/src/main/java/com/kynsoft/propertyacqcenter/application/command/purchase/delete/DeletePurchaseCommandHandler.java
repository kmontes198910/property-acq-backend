package com.kynsoft.propertyacqcenter.application.command.purchase.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class DeletePurchaseCommandHandler implements ICommandHandler<DeletePurchaseCommand> {

    private final IPurchaseService purchaseService;

    public DeletePurchaseCommandHandler(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public void handle(DeletePurchaseCommand command) {
        this.purchaseService.delete(command.getId());
    }
}
