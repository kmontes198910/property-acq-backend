package com.kynsoft.propertyacqcenter.application.command.bankDocument.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBankDocumentRequest {

    private String document;
    private UUID bankAccount;
    private String fileName;
}
