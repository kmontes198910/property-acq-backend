package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerPersonalBankStatements;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateSellerPersonalBankStatementsRequestCommandHandler implements ICommandHandler<UpdateSellerPersonalBankStatementsRequestCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateSellerPersonalBankStatementsRequestCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateSellerPersonalBankStatementsRequestCommand command) {

        adquisitionPropertyService.updateSellerPersonalBankStatements(command.getId(), this.convertListToDbFormat(command.getSellerPersonalBankStatements()));
    }

    public String convertListToDbFormat(List<CreateDocumentRequest> bankStatementRequest) {
        return bankStatementRequest.stream()
                .map(req -> req.getFileName() + "|" + req.getFilePath())
                .collect(Collectors.joining("|"));
    }
}
