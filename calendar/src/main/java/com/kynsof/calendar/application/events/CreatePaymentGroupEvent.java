package com.kynsof.calendar.application.events;

import com.kynsof.calendar.application.service.http.CreateGroupPaymentUnifRequest;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePaymentGroupEvent {
    private final CreateGroupPaymentUnifRequest createGroupPayment;
    private final UUID receiptId;

    public CreatePaymentGroupEvent(CreateGroupPaymentUnifRequest createGroupPayment, UUID receiptId) {
        this.createGroupPayment = createGroupPayment;
        this.receiptId = receiptId;
    }
}
