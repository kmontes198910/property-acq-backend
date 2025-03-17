package com.kynsof.payment.application.command.groupPayment.update;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGroupPaymentRequest {
    private String requestId;
    private String authorizationCode;
    private String reference;
    private String processUrl;
    private GroupPaymentStatus status;

}
