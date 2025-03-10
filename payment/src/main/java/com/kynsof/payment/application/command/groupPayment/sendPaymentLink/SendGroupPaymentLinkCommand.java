package com.kynsof.payment.application.command.groupPayment.sendPaymentLink;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SendGroupPaymentLinkCommand implements ICommand {

    private  UUID id;
    private String type;


    @Override
    public ICommandMessage getMessage() {
        return new SendGroupPaymentLinkMessage(id);
    }
}
