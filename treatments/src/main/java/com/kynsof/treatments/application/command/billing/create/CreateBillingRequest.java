package com.kynsof.treatments.application.command.billing.create;

import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingRequest {
    private  UUID patientId;
    private  UUID businessId;
    private  String code;
    private  String description;
    private BillingStatus status;
    private boolean isProforma;
    private Double cost;
    private String insuranceId;
}
