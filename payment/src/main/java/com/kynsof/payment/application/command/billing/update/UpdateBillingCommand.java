package com.kynsof.payment.application.command.billing.update;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBillingCommand implements ICommand {

    private UUID id;
    private final   String code;
    private final   String description;
    private final BillingStatus status;
    private final boolean isProforma;
    private final Double cost;
    private String insuranceId;

    public UpdateBillingCommand(UUID id, String code, String description, BillingStatus status, boolean isProforma, Double cost) {
        this.id = id;

        this.code = code;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
        this.cost = cost;
    }

    public static UpdateBillingCommand fromRequest(UpdateBillingRequest request, UUID id) {
        return new UpdateBillingCommand(
                id,
                request.getCode(),
                request.getDescription(),
                request.getStatus(),
                request.isProforma(),
                request.getCost()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBillingMessage(id);
    }
}
