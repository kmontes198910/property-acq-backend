package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyContactCommandHandler implements ICommandHandler<CreateCompanyContactCommand> {

    private final ICompanyContactService companyContactService;
    private final ICompanyService companyService;
    private final ISubCategoryService subCategoryService;

    public CreateCompanyContactCommandHandler(ICompanyContactService companyContactService, ICompanyService companyService, ISubCategoryService subCategoryService) {
        this.companyContactService = companyContactService;
        this.companyService = companyService;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public void handle(CreateCompanyContactCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        SubCategoryDto subCategoryDto = this.subCategoryService.findById(command.getSubCategory());
        this.companyContactService.validateEmail(command.getEmail(), command.getId());
        //this.companyContactService.validatePersonEmail(command.getPersonalEmail(), command.getId());
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
                .personalEmail(command.getPersonalEmail())
                .subCategory(subCategoryDto)
                .build()
        );
    }
}
