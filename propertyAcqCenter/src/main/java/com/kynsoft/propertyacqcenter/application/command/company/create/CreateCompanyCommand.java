package com.kynsoft.propertyacqcenter.application.command.company.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyCommand implements ICommand {

    private UUID id;
    private UUID business;
    private UUID companyType;
    private UUID subCompanyType;
    private String title;
    private String notes;
    private String category;
    private UUID subCategory;
    private CompanyType type;
    private CreateTitleCompanyDataRequest titleCompany;

    public CreateCompanyCommand(UUID business, UUID companyType, UUID subCompanyType, String title, 
                                String notes,
                                String category, UUID subCategory, CompanyType type,
                                CreateTitleCompanyDataRequest titleCompany) {
        this.id = UUID.randomUUID();
        this.business = business;
        this.companyType = companyType;
        this.subCompanyType = subCompanyType;
        this.title = title;
        this.notes = notes;
        this.category = category;
        this.subCategory = subCategory;
        this.type = type;
        this.titleCompany = titleCompany;
    }

    public static CreateCompanyCommand fromRequest(CreateCompanyRequest request) {
        return new CreateCompanyCommand(
                request.getBusiness(),
                request.getCompanyType(),
                request.getSubCompanyType(),
                request.getTitle(),
                request.getNotes(),
                request.getCategory(),
                request.getSubCategory(),
                request.getType(),
                request.getTitleCompany()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateCompanyMessage(id);
    }
}