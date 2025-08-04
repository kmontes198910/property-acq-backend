package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateBankStatementRequestCommandHandler implements ICommandHandler<UpdateBankStatementRequestCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateBankStatementRequestCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateBankStatementRequestCommand command) {

        adquisitionPropertyService.updateBankStatementRequest(command.getId(), this.convertListToDbFormat(command.getBankStatementRequest()));
    }

    public String convertListToDbFormat(List<CreateDocumentRequest> bankStatementRequest) {
        return bankStatementRequest.stream()
                .map(req -> req.getFileName() + "|" + req.getFilePath())
                .collect(Collectors.joining("|"));
    }
}
