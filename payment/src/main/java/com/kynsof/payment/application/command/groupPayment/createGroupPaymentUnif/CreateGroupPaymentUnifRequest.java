package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
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
    private PaymentType paymentType;
    private GroupPaymentStatus paymentStatus;
    private String insuranceId;
    private TypeOperation typeOperation;
    private boolean isProforma;
    private String authorizationCode;
    private String reference;
}
