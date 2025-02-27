package com.kynsof.payment.application.command.billing.update;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBillingRequest {
    private  String code;
    private  String description;
    private BillingStatus status;
    private boolean isProforma;
    private Double cost;
    private String insuranceId;
}
