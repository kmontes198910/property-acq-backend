package com.kynsof.payment.application.command.accountReconciliation.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountReconciliationRequest {

    private String code;
    private String description;
    private double cost;
    private UUID business;
}
