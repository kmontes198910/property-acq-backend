package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateContractAddendumCommand implements ICommand {

    private UUID id;
    private List<UpdateDocumentContractAddendumRequest> contractAddendum;

    public UpdateContractAddendumCommand(UUID id, List<UpdateDocumentContractAddendumRequest> contractAddendum) {
        this.id = id;
        this.contractAddendum = contractAddendum;
    }

    public static UpdateContractAddendumCommand fromRequest(UpdateContractAddendumRequest request, UUID id) {
        return new UpdateContractAddendumCommand(
                id,
                request.getContractAddendum()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateContractAddendumMessage(id);
    }
}
