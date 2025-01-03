package com.kynsof.treatments.application.command.businessProcedure.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CreateBusinessProcedureCommand implements ICommand {

    private boolean result;
    private UUID idBusiness;
    private Set<BusinessProcedurePriceRequest> procedurePrices;
    private final UUID insuranceId;

    public CreateBusinessProcedureCommand(UUID idBusiness, Set<BusinessProcedurePriceRequest> services, UUID insuranceId) {
        this.idBusiness = idBusiness;
        this.procedurePrices = Set.copyOf(services);

        this.insuranceId = insuranceId;
    }

    public static CreateBusinessProcedureCommand fromRequest(CreateBusinessProcedureRequest request) {
        return new CreateBusinessProcedureCommand(
                request.getIdBusiness(), 
                request.getProcedurePrices(),
                request.getInsuranceId());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBusinessProcedureMessage(result);
    }
}
