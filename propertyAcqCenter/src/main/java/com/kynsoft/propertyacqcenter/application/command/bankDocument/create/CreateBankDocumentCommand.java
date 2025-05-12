package com.kynsoft.propertyacqcenter.application.command.bankDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBankDocumentCommand implements ICommand {

    private UUID id;
    private String document;
    private UUID bankAccount;
    private String fileName;

    public CreateBankDocumentCommand(String document, UUID bankAccount, String fileName) {
        this.id = UUID.randomUUID();
        this.document = document;
        this.bankAccount = bankAccount;
        this.fileName = fileName;
    }

    public static CreateBankDocumentCommand fromRequest(CreateBankDocumentRequest request) {
        return new CreateBankDocumentCommand(
                request.getDocument(),
                request.getBankAccount(),
                request.getFileName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBankDocumentMessage(id);
    }
}
