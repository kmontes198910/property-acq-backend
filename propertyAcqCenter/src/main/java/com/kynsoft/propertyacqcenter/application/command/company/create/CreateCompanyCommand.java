package com.kynsoft.propertyacqcenter.application.command.company.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
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
    private Double ownershipPercentage;
    private Boolean signatureAuthority;
    private String notes;
    private String category;
    private UUID subCategory;

    public CreateCompanyCommand(UUID business, UUID companyType, UUID subCompanyType, String title, 
                                Double ownershipPercentage, Boolean signatureAuthority, String notes,
                                String category, UUID subCategory) {
        this.id = UUID.randomUUID();
        this.business = business;
        this.companyType = companyType;
        this.subCompanyType = subCompanyType;
        this.title = title;
        this.ownershipPercentage = ownershipPercentage;
        this.signatureAuthority = signatureAuthority;
        this.notes = notes;
        this.category = category;
        this.subCategory = subCategory;
    }

    public static CreateCompanyCommand fromRequest(CreateCompanyRequest request) {
        return new CreateCompanyCommand(
                request.getBusiness(),
                request.getCompanyType(),
                request.getSubCompanyType(),
                request.getTitle(),
                request.getOwnershipPercentage(),
                request.getSignatureAuthority(),
                request.getNotes(),
                request.getCategory(),
                request.getSubCategory()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateCompanyMessage(id);
    }
}