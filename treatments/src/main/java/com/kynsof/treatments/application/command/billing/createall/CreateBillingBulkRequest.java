package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingBulkRequest {
    private  UUID patientId;
    private  UUID businessId;
    private boolean isProforma;
}
