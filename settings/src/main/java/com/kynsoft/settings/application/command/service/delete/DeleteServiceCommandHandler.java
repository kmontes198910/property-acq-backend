package com.kynsoft.settings.application.command.service.delete;


import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.services.IServiceService;
import org.springframework.stereotype.Component;

@Component
public class DeleteServiceCommandHandler implements ICommandHandler<ServiceDeleteCommand> {

    private final IServiceService service;

    public DeleteServiceCommandHandler(IServiceService service) {
        this.service = service;
    }

    @Override
    public void handle(ServiceDeleteCommand command) {

        service.delete(command.getId());
    }

}
