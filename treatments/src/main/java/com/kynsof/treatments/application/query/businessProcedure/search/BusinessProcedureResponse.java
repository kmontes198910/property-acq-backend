package com.kynsof.treatments.application.query.businessProcedure.search;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.application.query.business.search.BusinessResponse;
import com.kynsof.treatments.application.query.procedure.getAll.ProcedureResponse;
import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import com.kynsof.treatments.domain.dto.InsuranceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class BusinessProcedureResponse implements IResponse {
    private UUID id;
    private BusinessResponse business;
    private ProcedureResponse procedure;
    private Double price;
    private String code;
    private InsuranceDto insurance;


    public BusinessProcedureResponse(BusinessProcedureDto object) {
        this.id = object.getId();
        this.business = new BusinessResponse(object.getBusiness());
        this.procedure = new ProcedureResponse(object.getProcedure());
        this.price = object.getPrice();
        this.code = object.getCode();
        this.insurance = object.getInsurance();
    }

}