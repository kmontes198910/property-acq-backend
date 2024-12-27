package com.kynsof.treatments.application.command.billing.create;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.rules.medicines.MedicinesNameMustBeUniqueRule;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import org.springframework.stereotype.Component;

@Component
public class CreateBillingCommandHandler implements ICommandHandler<CreateBillingCommand> {

    private final IBillingService serviceImpl;

    public CreateBillingCommandHandler(IBillingService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(CreateBillingCommand command) {

        BillingDto create = new BillingDto(
                command.getId(), 
                command.getPatientId(),
                command.getBusinessId(),
                command.getCode(),
                command.getDescription(),
                command.getStatus(),
                command.isProforma(),
                command.getCost()
        );
        this.serviceImpl.create(create);
    }
}
