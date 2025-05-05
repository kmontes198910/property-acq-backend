package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyContactCommandHandler implements ICommandHandler<CreateCompanyContactCommand> {

    private final ICompanyContactService companyContactService;
    private final ICompanyService companyService;

    public CreateCompanyContactCommandHandler(ICompanyContactService companyContactService, ICompanyService companyService) {
        this.companyContactService = companyContactService;
        this.companyService = companyService;
    }

    @Override
    public void handle(CreateCompanyContactCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        companyContactService.create(CompanyContactDto.builder()
                .id(command.getId())
                .category(command.getCategory())
                .company(companyDto)
                .department(command.getDepartment())
                .email(command.getEmail())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .isActive(command.getIsActive())
                .notes(command.getNotes())
                .phoneNumber(command.getPhoneNumber())
                .position(command.getPosition())
                .build()
        );
    }
}
