package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.buyerPersonalBankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBuyerPersonalBankStatementRequestCommand implements ICommand {

    private UUID id;
    private List<CreateDocumentRequest> buyerPersonalBankStatements;

    public UpdateBuyerPersonalBankStatementRequestCommand(UUID id, List<CreateDocumentRequest> buyerPersonalBankStatements) {
        this.id = id;
        this.buyerPersonalBankStatements = buyerPersonalBankStatements;
    }

    public static UpdateBuyerPersonalBankStatementRequestCommand fromRequest(UpdateBuyerPersonalBankStatementRequestRequest request, UUID id) {
        return new UpdateBuyerPersonalBankStatementRequestCommand(
                id,
                request.getBuyerPersonalBankStatements()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBuyerPersonalBankStatementRequestMessage(id);
    }
}
