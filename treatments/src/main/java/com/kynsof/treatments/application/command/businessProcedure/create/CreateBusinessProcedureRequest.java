package com.kynsof.treatments.application.command.businessProcedure.create;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CreateBusinessProcedureRequest {
    private UUID idBusiness;
    private UUID insuranceId;
    private Set<BusinessProcedurePriceRequest> procedurePrices;
}
