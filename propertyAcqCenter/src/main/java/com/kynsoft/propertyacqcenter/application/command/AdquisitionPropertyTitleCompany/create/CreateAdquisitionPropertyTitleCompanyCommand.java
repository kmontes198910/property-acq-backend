package com.kynsoft.propertyacqcenter.application.command.AdquisitionPropertyTitleCompany.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAdquisitionPropertyTitleCompanyCommand implements ICommand {

    private UUID id;
    private UUID buyer;
    private String property;
    private UUID contact;

    private String titleCommitment;
    private List<CreateDocumentRequest> documents;

    public CreateAdquisitionPropertyTitleCompanyCommand(UUID buyer, String property, UUID contact, String titleCommitment,
                                            List<CreateDocumentRequest> documents) {
        this.id = UUID.randomUUID();
        this.buyer = buyer;
        this.property = property;
        this.contact = contact;
        this.titleCommitment = titleCommitment;
        this.documents = documents;
    }

    public static CreateAdquisitionPropertyTitleCompanyCommand fromRequest(CreateAdquisitionPropertyTitleCompanyRequest request) {
        return new CreateAdquisitionPropertyTitleCompanyCommand(
                request.getBuyer(),
                request.getProperty(),
                request.getContact(),
                request.getTitleCommitment(),
                request.getDocuments()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAdquisitionPropertyTitleCompanyMessage(id);
    }
}
