package com.kynsoft.propertyacqcenter.application.command.titleCompany.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateTitleCompanyCommand implements ICommand {

    private UUID id;
    private UUID business;
    private UUID companyType;
    private UUID subCompanyType;
    private String title;
    private String notes;
    private String category;
    private UUID subCategory;
    private CreateTitleCompanyDataRequest titleCompany;

    public CreateTitleCompanyCommand(UUID business, UUID companyType, UUID subCompanyType, String title, 
                                String notes, String category, UUID subCategory,
                                CreateTitleCompanyDataRequest titleCompany) {
        this.id = UUID.randomUUID();
        this.business = business;
        this.companyType = companyType;
        this.subCompanyType = subCompanyType;
        this.title = title;
        this.notes = notes;
        this.category = category;
        this.subCategory = subCategory;
        this.titleCompany = titleCompany;
    }

    public static CreateTitleCompanyCommand fromRequest(CreateTitleCompanyRequest request) {
        return new CreateTitleCompanyCommand(
                request.getBusiness(),
                request.getCompanyType(),
                request.getSubCompanyType(),
                request.getTitle(),
                request.getNotes(),
                request.getCategory(),
                request.getSubCategory(),
                request.getTitleCompany()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateTitleCompanyMessage(id);
    }
}