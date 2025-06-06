package com.kynsoft.settings.application.command.address.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.services.IAddressService;
import org.springframework.stereotype.Component;

@Component
public class DeleteAddressCommandHandler implements ICommandHandler<DeleteAddressCommand> {

    private final IAddressService addressService;

    public DeleteAddressCommandHandler(IAddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void handle(DeleteAddressCommand command) {

        addressService.delete(command.getId());
    }

}
