package com.kynsof.payment.application.command.groupPayment.reversebyId;

import com.kynsof.payment.application.command.groupPayment.delete.DeleteGroupPaymentCommand;
import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class ReverseByIdGroupPaymentQueryQueryHandler implements ICommandHandler<ReverseByIdGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;

    public ReverseByIdGroupPaymentQueryQueryHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }


    @Override
    public void handle(ReverseByIdGroupPaymentCommand query) {
      serviceImpl.reverse(query.getId());

    }
}
