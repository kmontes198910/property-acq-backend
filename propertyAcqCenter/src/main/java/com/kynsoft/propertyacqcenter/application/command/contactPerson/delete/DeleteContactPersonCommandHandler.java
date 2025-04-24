package com.kynsoft.propertyacqcenter.application.command.contactPerson.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class DeleteContactPersonCommandHandler implements ICommandHandler<DeleteContactPersonCommand> {

    private final IContactPersonService contactPersonService;

    public DeleteContactPersonCommandHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public void handle(DeleteContactPersonCommand command) {
        this.contactPersonService.delete(command.getId());
    }
}
