package com.kynsoft.propertyacqcenter.application.command.companyAddress.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyAddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyAddressService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyAddressCommandHandler implements ICommandHandler<CreateCompanyAddressCommand> {

    private final ICompanyAddressService companyAddressService;
    private final ICompanyService companyService;

    public CreateCompanyAddressCommandHandler(ICompanyAddressService companyAddressService,
                                              ICompanyService companyService) {
        this.companyAddressService = companyAddressService;
        this.companyService = companyService;
    }

    @Override
    public void handle(CreateCompanyAddressCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        companyAddressService.create(
                CompanyAddressDto
                        .builder()
                        .id(command.getId())
                        .company(companyDto)
                        .addressType(command.getAddressType())
                        .streetAddress1(command.getStreetAddress1())
                        .streetAddress2(command.getStreetAddress2())
                        .city(command.getCity())
                        .state(command.getState())
                        .zipCode(command.getZipCode())
                        .country(command.getCountry())
                        .isPrimary(command.getIsPrimary())
                        .build()
        );
    }
}
