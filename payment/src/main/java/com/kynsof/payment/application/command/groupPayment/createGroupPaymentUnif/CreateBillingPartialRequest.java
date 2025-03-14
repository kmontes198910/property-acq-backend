package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBillingPartialRequest {

    private String code;
    private String description;
    private Double cost;

}
