package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSellerBankStatementRequest {

    private List<CreateDocumentRequest> sellerBankStatementRequest;
}
