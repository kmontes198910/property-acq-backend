package com.kynsof.treatments.application.command.billing.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingCommand implements ICommand {

    private  UUID id;
    private final   UUID patientId;
    private final   UUID businessId;
    private final   String code;
    private final   String description;
    private final BillingStatus status;
    private final boolean isProforma;
    private final Double cost;
    private final String insuranceId;

    public CreateBillingCommand(UUID patientId, UUID businessId, String code, String description, BillingStatus status, boolean isProforma, Double cost, String insuranceId) {
        this.patientId = patientId;
        this.businessId = businessId;
        this.code = code;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
        this.cost = cost;
        this.insuranceId = insuranceId;
    }


    public static CreateBillingCommand fromRequest(CreateBillingRequest request) {
        return new CreateBillingCommand(
                request.getPatientId(),
                request.getBusinessId(),
                request.getCode(),
                request.getDescription(),
                request.getStatus(),
                request.isProforma(),
                request.getCost(),
                request.getInsuranceId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBillingMessage(id);
    }
}
