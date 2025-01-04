package com.kynsof.treatments.application.command.billing.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.InsuranceDto;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.rules.medicines.MedicinesNameMustBeUniqueRule;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IInsuranceService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateBillingCommandHandler implements ICommandHandler<UpdateBillingCommand> {

    private final IBillingService serviceImpl;
    private final IInsuranceService insuranceService;

    public UpdateBillingCommandHandler(IBillingService serviceImpl, IInsuranceService insuranceService) {
        this.serviceImpl = serviceImpl;
        this.insuranceService = insuranceService;
    }

    @Override
    public void handle(UpdateBillingCommand command) {

        BillingDto update = this.serviceImpl.findById(command.getId());
        update.setCode(command.getCode());
        update.setStatus(command.getStatus());
        update.setDescription(command.getDescription());
        update.setProforma(command.isProforma());
        update.setCost(command.getCost());

        if(command.getInsuranceId() != null) {
            InsuranceDto insuranceDto = insuranceService.findById(UUID.fromString(command.getInsuranceId()));
            update.setInsurance(insuranceDto);
        }

        this.serviceImpl.update(update);
    }
}
