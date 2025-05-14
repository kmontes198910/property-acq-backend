package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateOwnerShipLegalEntityCommand implements ICommand {

    private UUID id;
    private String name;
    private Double ownershipPercentage;
    private UUID legalEntity;

    public CreateOwnerShipLegalEntityCommand(String name, Double ownershipPercentage, UUID legalEntity) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.ownershipPercentage = ownershipPercentage;
        this.legalEntity = legalEntity;
    }

    public static CreateOwnerShipLegalEntityCommand fromRequest(CreateOwnerShipLegalEntityRequest request){
        return new CreateOwnerShipLegalEntityCommand(
                request.getName(),
                request.getOwnershipPercentage(),
                request.getLegalEntity()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateOwnerShipLegalEntityMessage(id);
    }
}
