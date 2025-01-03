package com.kynsof.treatments.application.command.businessProcedure.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBusinessProcedureCommand implements ICommand {

    private boolean result;
    private UUID businessProcedureId;
    private double price;
    private String code;
    private final UUID insuranceId;

    public UpdateBusinessProcedureCommand(UUID businessProcedureId, double price, String code, UUID insuranceId) {
        this.businessProcedureId = businessProcedureId;
        this.price = price;
        this.code = code;
        this.insuranceId = insuranceId;
    }

    public static UpdateBusinessProcedureCommand fromRequest( BusinessProcedurePriceUpdateRequest request) {
        return new UpdateBusinessProcedureCommand(request.getBusinessProcedureId(), request.getPrice(), request.getCode(),
                request.getInsuranceId());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBusinessProcedureMessage(true);
    }
}
