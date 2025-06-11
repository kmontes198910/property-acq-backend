package com.kynsoft.settings.application.command.serviceType.delete;


import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.services.IServiceTypeService;
import org.springframework.stereotype.Component;

@Component
public class DeleteServiceTypeCommandHandler implements ICommandHandler<DeleteServiceTypeCommand> {

    private final IServiceTypeService service;

    public DeleteServiceTypeCommandHandler(IServiceTypeService service) {
        this.service = service;
    }

    @Override
    public void handle(DeleteServiceTypeCommand command) {

        service.delete(command.getId());
    }

}
