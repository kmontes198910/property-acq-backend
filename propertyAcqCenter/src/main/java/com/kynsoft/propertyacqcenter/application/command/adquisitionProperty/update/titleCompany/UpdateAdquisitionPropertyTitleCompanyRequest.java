package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAdquisitionPropertyTitleCompanyRequest {

    private LocalDate requestForEstoppelLetter;
    private CreateDocumentRequest earnestMoneyDepositConfirmation;
}
