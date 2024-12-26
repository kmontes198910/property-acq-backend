package com.kynsof.patients.application.command.patientInsurance.delete;

import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IPatientInsuranceService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientInsuranceCommandHandler implements ICommandHandler<DeletePatientInsuranceCommand> {

    private final IPatientInsuranceService serviceImpl;

    public DeletePatientInsuranceCommandHandler(IPatientInsuranceService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeletePatientInsuranceCommand command) {

        serviceImpl.delete(command.getId());
    }

}
