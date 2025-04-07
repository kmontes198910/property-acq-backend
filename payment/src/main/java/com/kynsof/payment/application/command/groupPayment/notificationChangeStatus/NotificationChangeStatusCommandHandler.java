package com.kynsof.payment.application.command.groupPayment.notificationChangeStatus;

import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class NotificationChangeStatusCommandHandler implements ICommandHandler<NotificationChangeStatusCommand> {

    private final IGroupPaymentService serviceImpl;

    public NotificationChangeStatusCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(NotificationChangeStatusCommand command) {
        this.serviceImpl.findByRequestId(command.getRequestId());
    }

}
