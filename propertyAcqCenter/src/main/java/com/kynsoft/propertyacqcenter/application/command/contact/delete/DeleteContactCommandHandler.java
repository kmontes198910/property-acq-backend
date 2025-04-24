package com.kynsoft.propertyacqcenter.application.command.contact.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import org.springframework.stereotype.Component;

@Component
public class DeleteContactCommandHandler implements ICommandHandler<DeleteContactCommand> {

    private final IContactService contactService;

    public DeleteContactCommandHandler(IContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void handle(DeleteContactCommand command) {
        this.contactService.delete(command.getId());
    }
}
