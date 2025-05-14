package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class UpdateOwnerShipLegalEntityCommand implements ICommand {

    private UUID id;
    private String name;
    private Double ownershipPercentage;
    private UUID legalEntity;

    public UpdateOwnerShipLegalEntityCommand(UUID id, String name, Double ownershipPercentage, UUID legalEntity) {
        this.id = id;
        this.name = name;
        this.ownershipPercentage = ownershipPercentage;
        this.legalEntity = legalEntity;
    }

    public static UpdateOwnerShipLegalEntityCommand fromRequest(UpdateOwnerShipLegalEntityRequest request, UUID id){
        return new UpdateOwnerShipLegalEntityCommand(
                id,
                request.getName(),
                request.getOwnershipPercentage(),
                request.getLegalEntity()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new UpdateOwnerShipLegalEntityMessage(id);
    }
}
