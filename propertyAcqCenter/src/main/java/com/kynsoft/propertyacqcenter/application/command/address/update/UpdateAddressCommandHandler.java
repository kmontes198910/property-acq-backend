package com.kynsoft.propertyacqcenter.application.command.address.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IAddressService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAddressCommandHandler implements ICommandHandler<UpdateAddressCommand> {

    private final IAddressService addressService;
    private final ILegalEntityService legalEntityService;

    public UpdateAddressCommandHandler(IAddressService addressService, ILegalEntityService legalEntityService) {
        this.addressService = addressService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdateAddressCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        addressService.update(new AddressDto(
                command.getId(), 
                legalEntityDto, 
                command.getAddressType(), 
                command.getStreetAddress1(), 
                command.getStreetAddress2(), 
                command.getCity(), 
                command.getState(), 
                command.getZipCode(), 
                command.getCountry(), 
                command.getIsPrimary(), 
                null, 
                null, 
                null, 
                null
        ));
    }
}
