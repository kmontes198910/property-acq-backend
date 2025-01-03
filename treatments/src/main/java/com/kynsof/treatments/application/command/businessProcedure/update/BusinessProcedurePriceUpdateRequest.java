package com.kynsof.treatments.application.command.businessProcedure.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BusinessProcedurePriceUpdateRequest {
    private UUID businessProcedureId;
    private double price;
    private String code;
    private UUID insuranceId;
}
