package com.kynsoft.propertyacqcenter.application.command.titleCompany.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class UpdateTitleCompanyCommand implements ICommand {

    private UUID id;
    private UUID business;
    private UUID companyType;
    private UUID subCompanyType;
    private String title;
    private String notes;
    private String category;
    private UUID subCategory;
    private UpdateTitleCompanyDataRequest titleCompany;

    public UpdateTitleCompanyCommand(UUID id, UUID business, UUID companyType, UUID subCompanyType, String title, 
                                String notes, String category, UUID subCategory,
                                UpdateTitleCompanyDataRequest titleCompany) {
        this.id = id;
        this.business = business;
        this.companyType = companyType;
        this.subCompanyType = subCompanyType;
        this.title = title;
        this.notes = notes;
        this.category = category;
        this.subCategory = subCategory;
        this.titleCompany = titleCompany;
    }

    public static UpdateTitleCompanyCommand fromRequest(UpdateTitleCompanyRequest request, UUID id) {
        return new UpdateTitleCompanyCommand(
                id,
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
        return new UpdateTitleCompanyMessage(id);
    }
}