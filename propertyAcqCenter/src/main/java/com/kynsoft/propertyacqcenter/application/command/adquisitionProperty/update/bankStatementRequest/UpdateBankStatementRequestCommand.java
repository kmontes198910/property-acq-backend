package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBankStatementRequestCommand implements ICommand {

    private UUID id;
    private List<CreateDocumentRequest> bankStatementRequest;

    public UpdateBankStatementRequestCommand(UUID id, List<CreateDocumentRequest> bankStatementRequest) {
        this.id = id;
        this.bankStatementRequest = bankStatementRequest;
    }

    public static UpdateBankStatementRequestCommand fromRequest(UpdateBankStatementRequestRequest request, UUID id) {
        return new UpdateBankStatementRequestCommand(
                id,
                request.getBankStatementRequest()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBankStatementRequestMessage(id);
    }
}
