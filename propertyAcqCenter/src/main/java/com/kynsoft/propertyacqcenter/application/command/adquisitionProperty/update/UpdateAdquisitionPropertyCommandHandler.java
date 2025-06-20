package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAdquisitionPropertyCommandHandler implements ICommandHandler<UpdateAdquisitionPropertyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;

    public UpdateAdquisitionPropertyCommandHandler(IAdquisitionPropertyService adquisitionPropertyService, 
                                                   ILegalEntityService legalEntityService,
                                                   IPropertyService propertyService,
                                                   ICompanyContactService companyContactService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
        this.legalEntityService = legalEntityService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
    }

    @Override
    public void handle(UpdateAdquisitionPropertyCommand command) {
        LegalEntityDto legalEntityDto = command.getBuyer() != null ? this.legalEntityService.findById(command.getBuyer()) : null;
        PropertyDto propertyDto = command.getProperty() != null ? this.propertyService.getById(command.getProperty()) : null;
        CompanyContactDto contactDto = command.getContact() != null ? this.companyContactService.findById(command.getContact()) : null;

        adquisitionPropertyService.update(AdquisitionPropertyDto
                .builder()
                .id(command.getId())
                .buyer(legalEntityDto)
                .property(propertyDto)
                .contact(contactDto)
                .buyerLicenseTagNo(command.getBuyerLicenseTagNo())
                .buyerNameAndYearVehicle(command.getBuyerNameAndYearVehicle())
                .build()
        );
    }

}
