package com.kynsof.payment.application.query.accountReconciliation.search;

import com.kynsof.payment.application.query.business.search.BusinessResponse;
import com.kynsof.payment.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AccountReconciliationResponse implements IResponse {

    private UUID id;
    private String code;
    private String description;
    private double cost;
    private BusinessResponse business;

    public AccountReconciliationResponse(AccountReconciliationDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.cost = dto.getCost();
        this.business = dto.getBusiness() != null ? new BusinessResponse(dto.getBusiness()) : null;
    }

}