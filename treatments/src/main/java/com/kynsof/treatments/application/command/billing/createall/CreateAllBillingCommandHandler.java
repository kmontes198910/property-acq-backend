package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.application.command.billing.create.CreateBillingRequest;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IBusinessService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateAllBillingCommandHandler implements ICommandHandler<CreateAllBillingCommand> {

    private final IBillingService serviceImpl;
    private final IPatientsService patientsService;
    private final IBusinessService businessService;

    public CreateAllBillingCommandHandler(IBillingService serviceImpl, IPatientsService patientsService, IBusinessService businessService) {

        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateAllBillingCommand command) {

        PatientDto patientDto = patientsService.findById(command.getPatientId());
        BusinessDto businessDto = businessService.findById(command.getBusinessId());
        List<BillingDto> billingDtoList = new ArrayList<>();
        command.getBillingPartialRequests().forEach(createBillingPartialRequest -> {
            BillingDto create = new BillingDto(
                    UUID.randomUUID(),
                    command.getPatientId(),
                    command.getBusinessId(),
                    createBillingPartialRequest.getCode(),
                    createBillingPartialRequest.getDescription(),
                    createBillingPartialRequest.getStatus(),
                    command.isProforma(),
                    createBillingPartialRequest.getCost()
            );

            create.setPatient(patientDto);
            create.setBusiness(businessDto);
            billingDtoList.add(create);
        });
            this.serviceImpl.createAll(billingDtoList);
            command.setResult(true);



    }
}
