package com.kynsof.treatments.application.command.billing.createall;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateBillingBulkRequest {
    private  UUID patientId;
    private  UUID businessId;
    private boolean isProforma;
    private List<CreateBillingPartialRequest> billingPartialRequests;
}
