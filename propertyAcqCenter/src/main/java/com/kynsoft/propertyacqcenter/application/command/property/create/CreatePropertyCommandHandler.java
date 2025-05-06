package com.kynsoft.propertyacqcenter.application.command.property.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreatePropertyCommandHandler implements ICommandHandler<CreatePropertyCommand> {

    private final IPropertyService propertyService;

    public CreatePropertyCommandHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreatePropertyCommand command) {
        propertyService.create(PropertyDto.builder()
                .addressLine1(command.getAddressLine1())
                .addressLine2(command.getAddressLine2())
                .apn(command.getApn())
                .city(command.getCity())
                .county(command.getCounty())
                .id(command.getId())
                .lotSize(command.getLotSize())
                .occupancy(command.getOccupancy())
                .propertyType(command.getPropertyType())
                .squareFootage(command.getSquareFootage())
                .state(command.getState())
                .unitCount(command.getUnitCount())
                .yearBuilt(command.getYearBuilt())
                .zipCode(command.getZipCode())
                .build());
    }
}
