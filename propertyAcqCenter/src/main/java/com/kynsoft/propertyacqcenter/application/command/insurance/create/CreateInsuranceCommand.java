package com.kynsoft.propertyacqcenter.application.command.insurance.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateInsuranceCommand implements ICommand {

    private UUID id;
    private String insuranceType;
    private String document;
    private UUID legalEntity;

    public CreateInsuranceCommand(String insuranceType, String document, UUID legalEntity) {
        this.id = UUID.randomUUID();
        this.insuranceType = insuranceType;
        this.document = document;
        this.legalEntity = legalEntity;
    }

    public static CreateInsuranceCommand fromRequest(CreateInsuranceRequest request) {
        return new CreateInsuranceCommand(
                request.getInsuranceType(),
                request.getDocument(),
                request.getLegalEntity()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateInsuranceMessage(id);
    }
}
