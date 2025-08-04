package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerPersonalBankStatements;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateSellerPersonalBankStatementsRequestCommand implements ICommand {

    private UUID id;
    private List<CreateDocumentRequest> sellerPersonalBankStatements;

    public UpdateSellerPersonalBankStatementsRequestCommand(UUID id, List<CreateDocumentRequest> sellerPersonalBankStatements) {
        this.id = id;
        this.sellerPersonalBankStatements = sellerPersonalBankStatements;
    }

    public static UpdateSellerPersonalBankStatementsRequestCommand fromRequest(UpdateSellerPersonalBankStatementsRequestRequest request, UUID id) {
        return new UpdateSellerPersonalBankStatementsRequestCommand(
                id,
                request.getSellerPersonalBankStatements()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSellerPersonalBankStatementsRequestMessage(id);
    }
}
