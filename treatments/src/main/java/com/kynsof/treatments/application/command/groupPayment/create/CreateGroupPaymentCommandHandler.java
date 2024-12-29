package com.kynsof.treatments.application.command.groupPayment.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IBusinessService;
import com.kynsof.treatments.domain.service.IGroupPaymentService;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateGroupPaymentCommandHandler implements ICommandHandler<CreateGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;

    public CreateGroupPaymentCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(CreateGroupPaymentCommand command) {
        UUID id = this.serviceImpl.createGroupPayment(command.getBillingIds());
        command.setId(id);
    }
}
