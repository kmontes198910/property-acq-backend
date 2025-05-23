package com.kynsof.patients.application.command.dependents.createByPatientId;

import com.kynsof.patients.domain.dto.DependentPatientDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateDependentByPatientIdCommandHandler implements ICommandHandler<CreateDependentByPatientIdCommand> {

    private final IPatientsService serviceImpl;

    public CreateDependentByPatientIdCommandHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(CreateDependentByPatientIdCommand command) {

        PatientDto prime = serviceImpl.findByIdSimple(command.getPrimeId());
        PatientDto dependent = serviceImpl.findByIdSimple(command.getPatientId());

        serviceImpl.updateDependent(new DependentPatientDto(
                dependent.getId(),
                dependent.getIdentificationType(),
                dependent.getIdentification(),
                dependent.getName(),
                dependent.getLastName(),
                dependent.getGender(),
                Status.ACTIVE,
                prime,
                dependent.getHasDisability(),
                dependent.getIsPregnant(),
                command.getFamilyRelationship(),
                dependent.getPhoto(),
                dependent.getDisabilityType(),
                dependent.getGestationTime()
        ));
        command.setId(dependent.getId());
    }
}
