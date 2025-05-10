package com.kynsoft.propertyacqcenter.application.command.insurance.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateInsuranceCommand implements ICommand {

    private UUID id;
    private String insuranceType;
    private String document;
    private UUID legalEntity;

    public UpdateInsuranceCommand(UUID id, String insuranceType, String document, UUID legalEntity) {
        this.id = id;
        this.insuranceType = insuranceType;
        this.document = document;
        this.legalEntity = legalEntity;
    }

    public static UpdateInsuranceCommand fromRequest(UpdateInsuranceRequest request, UUID id) {
        return new UpdateInsuranceCommand(
                id,
                request.getInsuranceType(),
                request.getDocument(),
                request.getLegalEntity()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateInsuranceMessage(id);
    }
}
