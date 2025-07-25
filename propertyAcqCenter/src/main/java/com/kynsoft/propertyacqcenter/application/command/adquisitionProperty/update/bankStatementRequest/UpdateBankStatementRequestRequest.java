package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBankStatementRequestRequest {

    private List<CreateDocumentRequest> bankStatementRequest;
}
