package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.buyerPersonalBankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateBuyerPersonalBankStatementRequestCommandHandler implements ICommandHandler<UpdateBuyerPersonalBankStatementRequestCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateBuyerPersonalBankStatementRequestCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateBuyerPersonalBankStatementRequestCommand command) {

        adquisitionPropertyService.updatebuyerPersonalBankStatements(command.getId(), this.convertListToDbFormat(command.getBuyerPersonalBankStatements()));
    }

    public String convertListToDbFormat(List<CreateDocumentRequest> bankStatementRequest) {
        return bankStatementRequest.stream()
                .map(req -> req.getFileName() + "|" + req.getFilePath())
                .collect(Collectors.joining("|"));
    }
}
