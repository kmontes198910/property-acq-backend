package com.kynsoft.propertyacqcenter.application.command.AdquisitionPropertyTitleCompany.create;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdquisitionPropertyTitleCompanyRequest {

    private UUID buyer;
    private String property;
    private UUID contact;
    private String titleCommitment;
    private List<CreateDocumentRequest> documents;
}
