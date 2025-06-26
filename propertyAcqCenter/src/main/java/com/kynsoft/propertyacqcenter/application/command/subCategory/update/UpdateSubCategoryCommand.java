package com.kynsoft.propertyacqcenter.application.command.subCategory.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateSubCategoryCommand implements ICommand {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private ContactType category;
    private CompanyType companyType;

    public UpdateSubCategoryCommand(UUID id, String name, String code, String description, ContactType category, CompanyType companyType) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.category = category;
        this.companyType = companyType;
    }

    public static UpdateSubCategoryCommand fromRequest(UpdateSubCategoryRequest request, UUID id) {
        return new UpdateSubCategoryCommand(
                id,
                request.getName(),
                request.getCode(),
                request.getDescription(),
                request.getCategory(),
                request.getCompanyType()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSubCategoryMessage(id);
    }
}
