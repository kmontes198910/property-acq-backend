package com.kynsof.patients.application.command.patients.update;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientsCommandHandler implements ICommandHandler<UpdatePatientsCommand> {

    private final IPatientsService serviceImpl;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;

    public UpdatePatientsCommandHandler(IPatientsService serviceImpl,
                                        IContactInfoService contactInfoService,
                                        IGeographicLocationService geographicLocationService) {
        this.serviceImpl = serviceImpl;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
    }

    @Override
    public void handle(UpdatePatientsCommand command) {
        ContactInfoDto contactInfoDto = contactInfoService.findByPatientId(command.getId());
        PatientDto patientDto = serviceImpl.findByIdSimple(command.getId());
        GeographicLocationDto parroquia =command.getCreateContactInfoRequest().getParroquia() != null ? geographicLocationService.findById(command.getCreateContactInfoRequest().getParroquia()) : null;

        patientDto.setIdentification(command.getIdentification());
        patientDto.setName(command.getName());
        patientDto.setLastName(command.getLastName());
        patientDto.setGender(command.getGender());
        patientDto.setPhoto(command.getPhoto());
        patientDto.setProfession(command.getProfession());
        patientDto.setEducationalLevel(command.getEducationalLevel());
        serviceImpl.update(patientDto);

        if (contactInfoDto.getId() == null) {
            contactInfoDto.setPatient(patientDto);
            contactInfoDto.setAddress(command.getCreateContactInfoRequest().getAddress());
            contactInfoDto.setTelephone(command.getCreateContactInfoRequest().getTelephone());
            contactInfoDto.setBirthdayDate(command.getCreateContactInfoRequest().getBirthdayDate());
            contactInfoDto.setEmail(command.getCreateContactInfoRequest().getEmail());
            contactInfoDto.setParroquia(parroquia);
            contactInfoDto.setStatus(Status.ACTIVE);
            contactInfoDto.setConventionalTelephone(command.getCreateContactInfoRequest().getConventionalTelephone());
            contactInfoDto.setMaritalStatus(command.getCreateContactInfoRequest().getMaritalStatus());
            contactInfoService.create(contactInfoDto);
        } else {
            contactInfoDto.setAddress(command.getCreateContactInfoRequest().getAddress());
            contactInfoDto.setTelephone(command.getCreateContactInfoRequest().getTelephone());
            contactInfoDto.setBirthdayDate(command.getCreateContactInfoRequest().getBirthdayDate());
            contactInfoDto.setParroquia(parroquia);
            contactInfoDto.setEmail(command.getCreateContactInfoRequest().getEmail());
            contactInfoDto.setStatus(Status.ACTIVE);
            contactInfoDto.setConventionalTelephone(command.getCreateContactInfoRequest().getConventionalTelephone());
            contactInfoDto.setMaritalStatus(command.getCreateContactInfoRequest().getMaritalStatus());
            contactInfoService.update(contactInfoDto);
        }

//        this.createCustomerEventService.create(new CustomerKafka(
//                patientDto.getId().toString(),
//                patientDto.getIdentification(),
//                patientDto.getName(),
//                patientDto.getLastName(),
//                contactInfoDto.getEmail(),
//                patientDto.getPhoto(),
//                contactInfoDto.getBirthdayDate(),
//                patientDto.getGender().name(),
//                contactInfoDto.getTelephone(),
//                patientDto.getProfession()
//        ));
    }
}
