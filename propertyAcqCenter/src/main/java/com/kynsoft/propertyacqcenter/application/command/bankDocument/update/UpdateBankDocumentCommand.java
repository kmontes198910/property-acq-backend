package com.kynsoft.propertyacqcenter.application.command.bankDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBankDocumentCommand implements ICommand {

    private UUID id;
    private String document;
    private UUID bankAccount;
    private String fileName;

    public UpdateBankDocumentCommand(UUID id, String document, UUID bankAccount, String fileName) {
        this.id = id;
        this.document = document;
        this.bankAccount = bankAccount;
        this.fileName = fileName;
    }

    public static UpdateBankDocumentCommand fromRequest(UpdateBankDocumentRequest request, UUID id) {
        return new UpdateBankDocumentCommand(
                id,
                request.getDocument(),
                request.getBankAccount(),
                request.getFileName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBankDocumentMessage(id);
    }
}
