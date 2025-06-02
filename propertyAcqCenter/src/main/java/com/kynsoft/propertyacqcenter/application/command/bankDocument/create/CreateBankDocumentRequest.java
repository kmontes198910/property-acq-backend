package com.kynsoft.propertyacqcenter.application.command.bankDocument.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBankDocumentRequest {

    private String document;
    private UUID bankAccount;
    private String fileName;
}
