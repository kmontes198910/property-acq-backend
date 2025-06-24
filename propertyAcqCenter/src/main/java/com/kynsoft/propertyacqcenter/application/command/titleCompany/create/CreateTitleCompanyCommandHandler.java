package com.kynsoft.propertyacqcenter.application.command.titleCompany.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.TitleCompanyDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class CreateTitleCompanyCommandHandler implements ICommandHandler<CreateTitleCompanyCommand> {

    private final ICompanyService companyService;
    private final IBusinessService businessService;
    private final ICompanyTypeService companyTypeService;
    private final ISubCompanyTypeService subCompanyTypeService;
    private final ISubCategoryService subCategoryService;

    public CreateTitleCompanyCommandHandler(@Qualifier("titleCompanyService") ICompanyService companyService,
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
    public void handle(CreateTitleCompanyCommand command) {
        BusinessDto businessDto = command.getBusiness() != null ? this.businessService.findById(command.getBusiness()) : null;
        CompanyTypeDto companyTypeDto = command.getCompanyType() != null ? this.companyTypeService.findById(command.getCompanyType()) : null;
        SubCompanyTypeDto subCompanyTypeDto = command.getSubCompanyType() != null ? this.subCompanyTypeService.findById(command.getSubCompanyType()) : null;
        SubCategoryDto subCategoryDto = command.getSubCategory() != null ? this.subCategoryService.findById(command.getSubCategory()) : null;

        this.companyService.create(CompanyDto
                .builder()
                .id(command.getId())
                .business(businessDto)
                .companyType(companyTypeDto)
                .subCompanyType(subCompanyTypeDto)
                .title(command.getTitle())
                .notes(command.getNotes())
                .category(command.getCategory())
                .subCategory(subCategoryDto)
                .titleCompany(TitleCompanyDto
                        .builder()
                        .titleReview(command.getTitleCompany().getTitleReview())
                        .copiesOfAnyExisting(command.getTitleCompany().getCopiesOfAnyExisting())
                        .copyOfLastRecordedDeed(command.getTitleCompany().getCopyOfLastRecordedDeed())
                        .existingTitlePolicy(command.getTitleCompany().getExistingTitlePolicy())
                        .legalDescriptionOfTheProperty(command.getTitleCompany().getLegalDescriptionOfTheProperty())
                        .oldTitleInsurancePolicy(command.getTitleCompany().getOldTitleInsurancePolicy())
                        .taxCertificates(command.getTitleCompany().getTaxCertificates())
                        .titleCommitment(command.getTitleCompany().getTitleCommitment())
                        .uccSearchResults(command.getTitleCompany().getUccSearchResults())
                        .build())
                .build()
        );
    }
}
