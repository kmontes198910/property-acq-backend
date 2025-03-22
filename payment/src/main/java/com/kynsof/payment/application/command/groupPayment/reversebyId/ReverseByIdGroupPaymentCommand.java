package com.kynsof.payment.application.command.groupPayment.reversebyId;

import com.kynsof.payment.application.command.groupPayment.delete.DeleteGroupPaymentMessage;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReverseByIdGroupPaymentCommand implements ICommand {

    private final UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new ReverseByIdGroupPaymentResponse(id);
    }

}
