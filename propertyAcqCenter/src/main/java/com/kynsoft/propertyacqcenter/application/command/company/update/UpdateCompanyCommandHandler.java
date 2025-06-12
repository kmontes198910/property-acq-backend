package com.kynsoft.propertyacqcenter.application.command.company.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;

@Component
public class UpdateCompanyCommandHandler implements ICommandHandler<UpdateCompanyCommand> {

    private final ICompanyService companyService;
    private final IBusinessService businessService;
    private final ICompanyTypeService companyTypeService;
    private final ISubCompanyTypeService subCompanyTypeService;
    private final ISubCategoryService subCategoryService;

    public UpdateCompanyCommandHandler(ICompanyService companyService, 
                                       IBusinessService businessService,
                                       ICompanyTypeService companyTypeService,
                                       ISubCompanyTypeService subCompanyTypeService,
                                       ISubCategoryService subCategoryService) {
        this.companyService = companyService;
        this.businessService = businessService;
        this.companyTypeService = companyTypeService;
        this.subCompanyTypeService = subCompanyTypeService;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public void handle(UpdateCompanyCommand command) {
        BusinessDto business = this.businessService.findById(command.getBusiness());
        CompanyTypeDto companyTypeDto = this.companyTypeService.findById(command.getCompanyType());
        SubCompanyTypeDto subCompanyTypeDto = this.subCompanyTypeService.findById(command.getSubCompanyType());
        SubCategoryDto subCategoryDto = this.subCategoryService.findById(command.getSubCategory());
        this.companyService.update(new CompanyDto(
                command.getId(),
                business,
                companyTypeDto,
                subCompanyTypeDto,
                command.getTitle(),
                command.getNotes(),
                null,
                null,
                null,
                null,
                command.getCategory(),
                subCategoryDto
        ));
    }
}
