package com.kynsof.payment.application.command.billing.create;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingRequest {
    private  UUID clientId;
    private  UUID businessId;
    private  String code;
    private  String description;
    private BillingStatus status;
    private boolean isProforma;
    private Double cost;
    private String insuranceId;
}
