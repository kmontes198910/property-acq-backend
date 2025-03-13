package com.kynsof.payment.application.command.groupPayment.updateAdminSystems;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGroupPaymentAdminSystemsRequest {
    private String authorizationCode;
    private String reference;
    private GroupPaymentStatus status;
    private PaymentType paymentType;

}
