package com.kynsof.payment.application.command.billing.create;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingCommand implements ICommand {

    private UUID id;
    private final UUID clientId;
    private UUID userSystemId;
    private String userSystemFullName;
    private final UUID businessId;
    private final String code;
    private final String description;
    private final BillingStatus status;
    private final boolean isProforma;
    private final Double cost;
    private final String insuranceId;
    private TypeOperation typeOperation;

    public CreateBillingCommand(UUID clientId, UUID businessId, String code, 
            String description, BillingStatus status, boolean isProforma, Double cost, 
            String insuranceId, UUID userSystemId, String userSystemFullName, TypeOperation typeOperation) {
        this.clientId = clientId;
        this.businessId = businessId;
        this.code = code;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
        this.cost = cost;
        this.insuranceId = insuranceId;
        this.userSystemId = userSystemId;
        this.userSystemFullName = userSystemFullName;
        this.typeOperation = typeOperation;
    }

    public static CreateBillingCommand fromRequest(CreateBillingRequest request) {
        return new CreateBillingCommand(
                request.getClientId(),
                request.getBusinessId(),
                request.getCode(),
                request.getDescription(),
                request.getStatus(),
                request.isProforma(),
                request.getCost(),
                request.getInsuranceId(),
                request.getUserSystemId(),
                request.getUserSystemFullName(),
                request.getTypeOperation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBillingMessage(id);
    }
}
