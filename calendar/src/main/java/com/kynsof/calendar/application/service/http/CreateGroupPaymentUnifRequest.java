package com.kynsof.calendar.application.service.http;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupPaymentUnifRequest {
    private UUID clientId;
    private UUID businessId;
    private List<CreateBillingPartialRequest> billings;
    private UUID userSystemId;
    private String userSystemFullName;
    private String paymentType;
    private String paymentStatus;
    private String insuranceId;
    private String typeOperation;
    private boolean isProforma;
    private String authorizationCode;
    private String reference;
}
