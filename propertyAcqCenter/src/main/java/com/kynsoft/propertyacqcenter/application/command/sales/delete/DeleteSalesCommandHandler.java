package com.kynsoft.propertyacqcenter.application.command.sales.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class DeleteSalesCommandHandler implements ICommandHandler<DeleteSalesCommand> {

    private final ISalesPropertyService salesPropertyService;

    public DeleteSalesCommandHandler(ISalesPropertyService salesPropertyService) {
        this.salesPropertyService = salesPropertyService;
    }

    @Override
    public void handle(DeleteSalesCommand command) {
        this.salesPropertyService.delete(command.getId());
    }
}
