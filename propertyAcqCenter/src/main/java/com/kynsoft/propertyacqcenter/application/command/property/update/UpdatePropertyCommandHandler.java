package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdatePropertyCommandHandler implements ICommandHandler<UpdatePropertyCommand> {

    private final IPropertyService propertyService;
    private final ICompanyService companyService;
    private final ILegalEntityService legalEntityService;

    public UpdatePropertyCommandHandler(IPropertyService propertyService, ICompanyService companyService, ILegalEntityService legalEntityService) {
        this.propertyService = propertyService;
        this.companyService = companyService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdatePropertyCommand command) {
        LegalEntityDto sellerName = command.getSellerName() != null ? this.legalEntityService.findById(command.getSellerName()) : null;
        CompanyDto sellerContactInfo = command.getSellerContactInfo() != null ? this.companyService.findById(command.getSellerContactInfo()) : null;
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
                .build());
    }
}
