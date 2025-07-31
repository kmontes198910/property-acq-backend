package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateSellerBankStatementCommandHandler implements ICommandHandler<UpdateSellerBankStatementCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateSellerBankStatementCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateSellerBankStatementCommand command) {

        adquisitionPropertyService.updateSellerBankStatementRequest(command.getId(), this.convertListToDbFormat(command.getSellerBankStatementRequest()));
    }

    public String convertListToDbFormat(List<CreateDocumentRequest> bankStatementRequest) {
        return bankStatementRequest.stream()
                .map(req -> req.getFileName() + "|" + req.getFilePath())
                .collect(Collectors.joining("|"));
    }
}
