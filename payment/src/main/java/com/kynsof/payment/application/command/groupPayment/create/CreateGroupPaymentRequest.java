package com.kynsof.payment.application.command.groupPayment.create;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupPaymentRequest {
    private List<UUID> billingIds;
    private UUID businessId;
    private UUID patientsId;
}
