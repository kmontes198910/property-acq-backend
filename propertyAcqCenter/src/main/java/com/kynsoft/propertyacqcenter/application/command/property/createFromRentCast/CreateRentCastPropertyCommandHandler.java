package com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IRentCastService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CreateRentCastPropertyCommandHandler implements ICommandHandler<CreateRentCastPropertyCommand> {

    private final IRentCastService rentCastService;

    public CreateRentCastPropertyCommandHandler(IRentCastService rentCastService) {
        this.rentCastService = rentCastService;
    }

    @Override
    public void handle(CreateRentCastPropertyCommand command) {
        List<UUID> id = this.rentCastService.createRentCastProperty(command.getPropertyResponses());
        command.setIds(id);
    }
}
