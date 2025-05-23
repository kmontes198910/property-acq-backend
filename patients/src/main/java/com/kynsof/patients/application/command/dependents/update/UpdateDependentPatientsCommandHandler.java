package com.kynsof.patients.application.command.dependents.update;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.DependentPatientDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateDependentPatientsCommandHandler implements ICommandHandler<UpdateDependentPatientsCommand> {

    private final IPatientsService serviceImpl;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;

    public UpdateDependentPatientsCommandHandler(IPatientsService serviceImpl,
                                                 IContactInfoService contactInfoService,
                                                 IGeographicLocationService geographicLocationService) {
        this.serviceImpl = serviceImpl;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
    }

    @Override
    public void handle(UpdateDependentPatientsCommand command) {
        ContactInfoDto contactInfoDto = contactInfoService.findByPatientId(command.getId());
        GeographicLocationDto parroquia = geographicLocationService.findById(command.getCreateContactInfoRequest().getParroquia());
        PatientDto prime = serviceImpl.findByIdSimple(command.getPrimeId());
        PatientDto dependent = serviceImpl.findByIdSimple(command.getId());

        DependentPatientDto update = new DependentPatientDto(
                command.getId(),
                command.getIdentificationType(),
                command.getIdentification(),
                command.getName(),
                command.getLastName(),
                command.getGender(),
                Status.ACTIVE,
                prime,
                command.getHasDisability(),
                command.getIsPregnant(),
                command.getFamilyRelationship(),
                command.getPhoto(),
                command.getDisabilityType(),
                command.getGestationTime()
        );
        serviceImpl.updateDependent(update);

        if (contactInfoDto.getEmail() == null) {
            contactInfoDto.setPatient(dependent);
            contactInfoDto.setAddress(command.getCreateContactInfoRequest().getAddress());
            contactInfoDto.setTelephone(command.getCreateContactInfoRequest().getTelephone());
            contactInfoDto.setBirthdayDate(command.getCreateContactInfoRequest().getBirthdayDate());
            contactInfoDto.setEmail(command.getCreateContactInfoRequest().getEmail());
            contactInfoDto.setParroquia(parroquia);
            contactInfoDto.setStatus(Status.ACTIVE);
            contactInfoService.create(contactInfoDto);
        } else {
            contactInfoDto.setAddress(command.getCreateContactInfoRequest().getAddress());
            contactInfoDto.setTelephone(command.getCreateContactInfoRequest().getTelephone());
            contactInfoDto.setBirthdayDate(command.getCreateContactInfoRequest().getBirthdayDate());
            contactInfoDto.setParroquia(parroquia);
            contactInfoDto.setEmail(command.getCreateContactInfoRequest().getEmail());
            contactInfoDto.setStatus(Status.ACTIVE);
            contactInfoService.update(contactInfoDto);
        }
    }
}
