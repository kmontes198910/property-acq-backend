package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateContractAddendumCommandHandler implements ICommandHandler<UpdateContractAddendumCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;
    private final IPropertyService propertyService;

    public UpdateContractAddendumCommandHandler(IAdquisitionPropertyService adquisitionPropertyService, IPropertyService propertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateContractAddendumCommand command) {

        String propertyId = adquisitionPropertyService.updatecontractAddendum(command.getId(), this.convertListToDbFormat(command.getContractAddendum()), command.getDate());
        this.propertyService.updateExpectedClosingDate(propertyId, command.getDate());
    }

    public String convertListToDbFormat(List<UpdateDocumentContractAddendumRequest> contractAddendumRequests) {
        return contractAddendumRequests.stream()
                .map(req -> req.getFileName() + "|" + req.getFilePath() + "|" + req.getClosingDate())
                .collect(Collectors.joining("|"));
    }
}
