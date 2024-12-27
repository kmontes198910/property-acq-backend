package com.kynsof.treatments.application.command.billing.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.rules.medicines.MedicinesNameMustBeUniqueRule;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import org.springframework.stereotype.Component;

@Component
public class UpdateBillingCommandHandler implements ICommandHandler<UpdateBillingCommand> {

    private final IBillingService serviceImpl;

    public UpdateBillingCommandHandler(IBillingService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(UpdateBillingCommand command) {

        BillingDto update = this.serviceImpl.findById(command.getId());


        this.serviceImpl.update(update);
    }
}
