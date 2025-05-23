package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdatePropertyCommandHandler implements ICommandHandler<UpdatePropertyCommand> {

    private final IPropertyService propertyService;

    public UpdatePropertyCommandHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdatePropertyCommand command) {
        propertyService.update(PropertyDto.builder()
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
                .roofType(command.getRoofType())
                .structureType(command.getStructureType())
                .hoa(command.getHoa())
                .bedrooms(command.getBedrooms())
                .bathrooms(command.getBathrooms())
                .askingPrice(command.getAskingPrice())
                .formattedAddress(command.getFormattedAddress())
                .propertyStatus(command.getPropertyStatus())
                .isManual(command.getIsManual())
                .build());
    }
}
