package com.kynsof.treatments.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BusinessProcedureDto {
    private UUID id;
    private BusinessDto business;
    private ProcedureDto procedure;
    private Double price;
    private String code;
    private InsuranceDto insurance;
}
