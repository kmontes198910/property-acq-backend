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
    private String fileName;

    public CreateInsuranceCommand(String insuranceType, String document, UUID legalEntity, String fileName) {
        this.id = UUID.randomUUID();
        this.insuranceType = insuranceType;
        this.document = document;
        this.legalEntity = legalEntity;
        this.fileName = fileName;
    }

    public static CreateInsuranceCommand fromRequest(CreateInsuranceRequest request) {
        return new CreateInsuranceCommand(
                request.getInsuranceType(),
                request.getDocument(),
                request.getLegalEntity(),
                request.getFileName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateInsuranceMessage(id);
    }
}
