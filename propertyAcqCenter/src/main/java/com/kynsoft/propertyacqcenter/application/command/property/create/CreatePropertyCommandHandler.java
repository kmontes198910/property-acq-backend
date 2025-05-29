package com.kynsoft.propertyacqcenter.application.command.property.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreatePropertyCommandHandler implements ICommandHandler<CreatePropertyCommand> {

    private final IPropertyService propertyService;
    private final ICompanyService companyService;
    private final ILegalEntityService legalEntityService;

    public CreatePropertyCommandHandler(IPropertyService propertyService, ICompanyService companyService, ILegalEntityService legalEntityService) {
        this.propertyService = propertyService;
        this.companyService = companyService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(CreatePropertyCommand command) {
        LegalEntityDto sellerName = this.legalEntityService.findById(command.getSellerName());
        CompanyDto sellerContactInfo = this.companyService.findById(command.getSellerContactInfo());
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
                .build()
        );
    }
}
