package com.kynsof.treatments.application.command.patients.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.Status;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreatePatientsCommandHandler  implements ICommandHandler<CreatePatientsCommand> {

    private final IPatientsService serviceImpl;

    public CreatePatientsCommandHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(CreatePatientsCommand command) {
       UUID id = serviceImpl.create(new PatientDto(
                UUID.randomUUID(),
                command.getIdentification(),
                command.getName(),
                command.getLastName(),
                command.getGender(),
               Status.ACTIVE,
               command.getBirthDate(),
               ""
        ));
       command.setId(id);
    }
}
