package com.kynsof.treatments.application.command.billing.update;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
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
}
