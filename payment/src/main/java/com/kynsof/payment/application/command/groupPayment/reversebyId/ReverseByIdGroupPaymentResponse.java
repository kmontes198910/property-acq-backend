package com.kynsof.payment.application.command.groupPayment.reversebyId;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;
@Getter
public class  ReverseByIdGroupPaymentResponse implements ICommandMessage {

    private final UUID id;

    private final String command = "REVERSE_GROUP_PAYMENT";

    public  ReverseByIdGroupPaymentResponse(UUID id) {
        this.id = id;
    }

}