package com.kynsof.treatments.application.command.billing.create;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.rules.medicines.MedicinesNameMustBeUniqueRule;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IBusinessService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

@Component
public class CreateBillingCommandHandler implements ICommandHandler<CreateBillingCommand> {

    private final IBillingService serviceImpl;
    private final IPatientsService patientsService;
    private final IBusinessService businessService;

    public CreateBillingCommandHandler(IBillingService serviceImpl, IPatientsService patientsService, IBusinessService businessService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateBillingCommand command) {

        //TODO
        //Validar que no existan dos registros con el mismo paciente,el mismo codigo y el mismo estado
        PatientDto patientDto = patientsService.findById(command.getPatientId());
        BusinessDto businessDto = businessService.findById(command.getBusinessId());

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

        create.setPatient(patientDto);
        create.setBusiness(businessDto);
        this.serviceImpl.create(create);
    }
}
