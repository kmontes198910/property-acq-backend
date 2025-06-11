package com.kynsoft.propertyacqcenter.application.command.company.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCompanyCommand implements ICommand {

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

    public static UpdateCompanyCommand fromRequest(UUID id, UpdateCompanyRequest request) {
        return new UpdateCompanyCommand(
                id,
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
        return new UpdateCompanyMessage(id);
    }
}
