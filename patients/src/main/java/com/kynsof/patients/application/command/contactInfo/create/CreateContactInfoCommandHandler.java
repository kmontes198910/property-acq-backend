package com.kynsof.patients.application.command.contactInfo.create;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateContactInfoCommandHandler implements ICommandHandler<CreateContactInfoCommand> {

    private final IContactInfoService contactInfoService;
    private final IPatientsService patientsService;
    private final IGeographicLocationService geographicLocationService;

    public CreateContactInfoCommandHandler(IContactInfoService serviceImpl, IPatientsService patientsService,
            IGeographicLocationService geographicLocationService) {
        this.contactInfoService = serviceImpl;
        this.patientsService = patientsService;
        this.geographicLocationService = geographicLocationService;
    }

    @Override
    public void handle(CreateContactInfoCommand command) {
        PatientDto patientDto = patientsService.findByIdSimple(command.getPatientId());
        GeographicLocationDto province = geographicLocationService.findById(command.getProvince());
        GeographicLocationDto canton = geographicLocationService.findById(command.getCanton());
        GeographicLocationDto parroquia = geographicLocationService.findById(command.getParroquia());

        ContactInfoDto contactInfoDto = contactInfoService.findByPatientId(command.getPatientId());

        if (contactInfoDto.getId() == null) {
            ContactInfoDto create = new ContactInfoDto(
                    UUID.randomUUID(),
                    patientDto,
                    "",
                    command.getTelephone(),
                    command.getAddress(),
                    command.getBirthdayDate(),
                    Status.ACTIVE,
                    parroquia
            );
            UUID id = contactInfoService.create(create);
            command.setId(id);
        } else {
            contactInfoDto.setPatient(patientDto);
            contactInfoDto.setAddress(command.getAddress());
            contactInfoDto.setBirthdayDate(command.getBirthdayDate());
            contactInfoDto.setTelephone(command.getTelephone());
            contactInfoDto.setStatus(Status.ACTIVE);
            contactInfoDto.setParroquia(parroquia);
            contactInfoService.update(contactInfoDto);
            command.setId(contactInfoDto.getId());
        }
        //TODO yannier Evento de confirmación de creacion de usuario y datos de como debe acceder, se envia un correo
    }
}
