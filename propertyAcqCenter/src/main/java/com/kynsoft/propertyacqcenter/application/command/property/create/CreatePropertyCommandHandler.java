package com.kynsoft.propertyacqcenter.application.command.property.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreatePropertyCommandHandler implements ICommandHandler<CreatePropertyCommand> {

    private final IPropertyService propertyService;
    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;

    public CreatePropertyCommandHandler(IPropertyService propertyService, IContactService contactService, ILegalEntityService legalEntityService) {
        this.propertyService = propertyService;
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(CreatePropertyCommand command) {
        LegalEntityDto sellerName = command.getSellerName() != null ? this.legalEntityService.findById(command.getSellerName()) : null;
        ContactDto sellerContactInfo = command.getSellerContactInfo() != null ? this.contactService.findById(command.getSellerContactInfo()) : null;
        this.propertyService.validatePropertyId(command.getId());
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
                .roofType(command.getRoofType())
                .structureType(command.getStructureType())
                .hoa(command.getHoa())
                .bedrooms(command.getBedrooms())
                .bathrooms(command.getBathrooms())
                .askingPrice(command.getAskingPrice())
                .formattedAddress(command.getFormattedAddress())
                .propertyStatus(command.getPropertyStatus())
                .isManual(command.getIsManual())
                .daysOnMarket(command.getDaysOnMarket())
                .purchasePrice(command.getRentalPrice())
                .rentalPrice(command.getRentalPrice())
                .afterRepairValue(command.getAfterRepairValue())
                .floodZoneDetermination(command.getFloodZoneDetermination())
                .propertyRented(command.getPropertyRented())

                .contractExecutionDate(command.getContractExecutionDate())
                .acquisitionType(command.getAcquisitionType())
                .sourceType(command.getSourceType())
                .sellerName(sellerName)
                .sellerContactInfo(sellerContactInfo)
                .expectedClosingDate(command.getExpectedClosingDate())
                .emdRequirements(command.getEmdRequirements())
                .emdOfferedAmount(command.getEmdOfferedAmount())

                .isMortgage(command.getIsMortgage())
                .distressed(command.getDistressed())
                .lenghOfOwership(command.getLenghOfOwership())
                .openBalanceMortagage(command.getOpenBalanceMortagage())
                .involuntaryLiensAmount(command.getInvoluntaryLiensAmount())
                .publicRecord(command.getPublicRecord())
                .mls(command.getMls())

                .buildingArea(command.getBuildingArea())
                .livingArea(command.getLivingArea())
                .grossArea(command.getGrossArea())
                .taxableArea(command.getTaxableArea())
                .garageArea(command.getGarageArea())

                .hasHoa(command.getHasHoa())
                .hoaName(command.getHoaName())
                .hoaType(command.getHoaType())
                .hoaFeeFrequency(command.getHoaFeeFrequency())
                .build()
        );
    }
}
