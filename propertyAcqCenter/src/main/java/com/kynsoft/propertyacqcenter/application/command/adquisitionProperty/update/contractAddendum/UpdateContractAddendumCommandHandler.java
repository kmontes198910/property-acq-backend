package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateContractAddendumCommandHandler implements ICommandHandler<UpdateContractAddendumCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateContractAddendumCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateContractAddendumCommand command) {

        adquisitionPropertyService.updatecontractAddendum(command.getId(), this.convertListToDbFormat(command.getContractAddendum()));
    }

    public String convertListToDbFormat(List<UpdateDocumentContractAddendumRequest> contractAddendumRequests) {
        return contractAddendumRequests.stream()
                .map(req -> req.getFileName() + "|" + req.getFilePath() + "|" + req.getClosingDate())
                .collect(Collectors.joining("|"));
    }
}
