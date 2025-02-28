package com.kynsof.payment.application.command.billing.createall;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBillingPartialRequest {

    private String code;
    private String description;
    private BillingStatus status;
    private Double cost;
}
