package com.kynsof.payment.application.command.groupPayment.notificationChangeStatus;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotificationChangeStatusMessage implements ICommandMessage {

    private final String id;

    private final String command = "CHANGE_STATUS";

    public NotificationChangeStatusMessage(String id) {
        this.id = id;
    }

}
