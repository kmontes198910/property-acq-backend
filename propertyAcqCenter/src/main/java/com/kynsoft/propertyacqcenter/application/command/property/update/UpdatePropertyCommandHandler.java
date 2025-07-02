package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UpdatePropertyCommandHandler implements ICommandHandler<UpdatePropertyCommand> {

    private final IPropertyService propertyService;
    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;
    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdatePropertyCommandHandler(IPropertyService propertyService, 
                                        IContactService contactService, 
                                        ILegalEntityService legalEntityService,
                                        IAdquisitionPropertyService adquisitionPropertyService) {
        this.propertyService = propertyService;
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdatePropertyCommand command) {
        LegalEntityDto sellerName = command.getSellerName() != null ? this.legalEntityService.findById(command.getSellerName()) : null;
        ContactDto sellerContactInfo = command.getSellerContactInfo() != null ? this.contactService.findById(command.getSellerContactInfo()) : null;
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
                .daysOnMarket(command.getDaysOnMarket())
                .purchasePrice(command.getPurchasePrice())
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

                .build());

        this.createAdquisition(command);
    }

    private void createAdquisition(UpdatePropertyCommand command) {
        if (command.getPropertyStatus().equals(PropertyStatus.ACQUISITION) && !this.adquisitionPropertyService.existsByPropertyId(command.getId())) {
            PropertyDto propertyDto = this.propertyService.getById(command.getId());
            adquisitionPropertyService.create(AdquisitionPropertyDto
                    .builder()
                    .id(UUID.randomUUID())
                    .property(propertyDto)
                    .build());
        }
    }
}

