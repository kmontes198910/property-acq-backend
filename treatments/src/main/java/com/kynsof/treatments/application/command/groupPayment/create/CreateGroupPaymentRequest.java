package com.kynsof.treatments.application.command.groupPayment.create;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupPaymentRequest {
    private List<UUID> billingIds;

}
