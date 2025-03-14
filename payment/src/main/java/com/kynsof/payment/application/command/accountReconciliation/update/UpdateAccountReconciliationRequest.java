package com.kynsof.payment.application.command.accountReconciliation.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountReconciliationRequest {

    private String code;
    private String description;
    private double cost;
}
