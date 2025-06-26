package com.kynsoft.propertyacqcenter.application.command.subCategory.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateSubCategoryCommand implements ICommand {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private ContactType category;
    private CompanyType companyType;

    public CreateSubCategoryCommand(String name, String code, String description, ContactType category, CompanyType companyType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.code = code;
        this.description = description;
        this.category = category;
        this.companyType = companyType;
    }

    public static CreateSubCategoryCommand fromRequest(CreateSubCategoryRequest request) {
        return new CreateSubCategoryCommand(
                request.getName(),
                request.getCode(),
                request.getDescription(),
                request.getCategory(),
                request.getCompanyType()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateSubCategoryMessage(id);
    }
}
