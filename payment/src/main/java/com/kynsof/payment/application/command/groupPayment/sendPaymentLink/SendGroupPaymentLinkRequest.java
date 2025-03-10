package com.kynsof.payment.application.command.groupPayment.sendPaymentLink;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SendGroupPaymentLinkRequest {
    private UUID groupPaymentId;
    private String type;
}
