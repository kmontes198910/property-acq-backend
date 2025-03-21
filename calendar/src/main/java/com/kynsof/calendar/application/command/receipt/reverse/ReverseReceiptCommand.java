package com.kynsof.calendar.application.command.receipt.reverse;

import com.kynsof.calendar.application.command.receipt.reschedule.RescheduleReceiptMessage;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReverseReceiptCommand implements ICommand {

    private final String requestId ;


    public ReverseReceiptCommand(String requestId) {
        this.requestId = requestId;
    }



    @Override
    public ICommandMessage getMessage() {
        return new RescheduleReceiptMessage(UUID.randomUUID());
    }
}
