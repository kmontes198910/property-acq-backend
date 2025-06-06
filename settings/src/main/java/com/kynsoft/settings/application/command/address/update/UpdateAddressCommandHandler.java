package com.kynsoft.settings.application.command.address.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.dto.AddressDto;
import com.kynsoft.settings.domain.services.IAddressService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAddressCommandHandler implements ICommandHandler<UpdateAddressCommand> {

    private final IAddressService addressService;

    public UpdateAddressCommandHandler(IAddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void handle(UpdateAddressCommand command) {

        addressService.update(new AddressDto(
                command.getId(), 
                command.getAddressType(), 
                command.getStreetAddress1(), 
                command.getStreetAddress2(), 
                command.getCity(), 
                command.getState(), 
                command.getZipCode(), 
                command.getCountry(), 
                null, 
                null, 
                null, 
                null,
                command.getNickName()
        ));
    }
}
