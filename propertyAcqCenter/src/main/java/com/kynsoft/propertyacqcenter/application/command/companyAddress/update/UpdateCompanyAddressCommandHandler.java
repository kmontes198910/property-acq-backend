package com.kynsoft.propertyacqcenter.application.command.companyAddress.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyAddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyAddressService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateCompanyAddressCommandHandler implements ICommandHandler<UpdateCompanyAddressCommand> {

    private final ICompanyAddressService companyAddressService;
    private final ICompanyService companyService;

    public UpdateCompanyAddressCommandHandler(ICompanyAddressService companyAddressService,
            ICompanyService companyService) {
        this.companyAddressService = companyAddressService;
        this.companyService = companyService;
    }

    @Override
    public void handle(UpdateCompanyAddressCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        if (command.getAddressType().equals(AddressType.PRINCIPAL)) {
            this.companyAddressService.validateAccountNumber(companyDto.getId(), command.getId());
        }

        companyAddressService.update(
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
                        .nickName(command.getNickName())
                        .build()
        );
    }
}
