package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateSellerBankStatementCommand implements ICommand {

    private UUID id;
    private List<CreateDocumentRequest> sellerBankStatementRequest;

    public UpdateSellerBankStatementCommand(UUID id, List<CreateDocumentRequest> sellerBankStatementRequest) {
        this.id = id;
        this.sellerBankStatementRequest = sellerBankStatementRequest;
    }

    public static UpdateSellerBankStatementCommand fromRequest(UpdateSellerBankStatementRequest request, UUID id) {
        return new UpdateSellerBankStatementCommand(
                id,
                request.getSellerBankStatementRequest()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSellerBankStatementMessage(id);
    }
}
