package com.kynsof.treatments.application.command.patientVaccine.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.service.IPatientVaccineService;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientVaccineCommandHandler implements ICommandHandler<PatientsPatientVaccineCommand> {

    private final IPatientVaccineService serviceImpl;

    public DeletePatientVaccineCommandHandler(IPatientVaccineService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(PatientsPatientVaccineCommand command) {

        serviceImpl.delete(command.getId());
    }

}
