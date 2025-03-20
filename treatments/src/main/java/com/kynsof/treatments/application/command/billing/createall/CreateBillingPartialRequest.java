package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBillingPartialRequest {
    private  String code;
    private  String description;
    private BillingStatus status;
    private Double cost;
}
