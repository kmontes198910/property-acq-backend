package com.kynsoft.propertyacqcenter.application.command.insurance.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.InsuranceType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateInsuranceCommand implements ICommand {

    private UUID id;
    private InsuranceType insuranceType;
    private String document;
    private UUID legalEntity;
    private String fileName;

    public UpdateInsuranceCommand(UUID id, InsuranceType insuranceType, String document, UUID legalEntity, String fileName) {
        this.id = id;
        this.insuranceType = insuranceType;
        this.document = document;
        this.legalEntity = legalEntity;
        this.fileName = fileName;
    }

    public static UpdateInsuranceCommand fromRequest(UpdateInsuranceRequest request, UUID id) {
        return new UpdateInsuranceCommand(
                id,
                request.getInsuranceType(),
                request.getDocument(),
                request.getLegalEntity(),
                request.getFileName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateInsuranceMessage(id);
    }
}
