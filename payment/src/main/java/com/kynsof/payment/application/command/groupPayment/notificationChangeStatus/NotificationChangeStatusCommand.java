package com.kynsof.payment.application.command.groupPayment.notificationChangeStatus;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NotificationChangeStatusCommand implements ICommand {

    private  String requestId;
    @Override
    public ICommandMessage getMessage() {
        return new NotificationChangeStatusMessage(requestId);
    }
}
