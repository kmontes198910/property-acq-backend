package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBillingPartialRequest {

    private String code;
    private String description;
    private Double cost;

}
