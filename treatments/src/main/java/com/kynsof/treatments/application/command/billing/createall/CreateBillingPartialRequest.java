package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingPartialRequest {
    private  UUID patientId;
    private  UUID businessId;
    private  String code;
    private  String description;
    private BillingStatus status;

    private Double cost;
}
