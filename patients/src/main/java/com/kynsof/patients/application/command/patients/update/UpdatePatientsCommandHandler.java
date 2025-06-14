package com.kynsof.patients.application.command.patients.update;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDto;
import com.kynsof.patients.infrastructure.services.rabbitMQ.eventPublisher.EventPatientPublisherService;
import com.kynsof.patients.infrastructure.services.rabbitMQ.patientCreate.Person;
import com.kynsof.patients.infrastructure.services.rabbitMQ.patientUpdate.UpdatePatientProducer;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdatePatientsCommandHandler implements ICommandHandler<UpdatePatientsCommand> {

    private final IPatientsService serviceImpl;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;
    private final UpdatePatientProducer updatePatientProducer;
    private final EventPatientPublisherService patientPublisherService;

    public UpdatePatientsCommandHandler(IPatientsService serviceImpl,
            IContactInfoService contactInfoService,
            IGeographicLocationService geographicLocationService,
            UpdatePatientProducer updatePatientProducer,
            EventPatientPublisherService patientPublisherService) {
        this.serviceImpl = serviceImpl;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
        this.updatePatientProducer = updatePatientProducer;
        this.patientPublisherService = patientPublisherService;
    }

    @Override
    public void handle(UpdatePatientsCommand command) {
        ContactInfoDto contactInfoDto = contactInfoService.findByPatientId(command.getId());
        PatientDto patientDto = serviceImpl.findByIdSimple(command.getId());
        GeographicLocationDto parroquia = command.getCreateContactInfoRequest().getParroquia() != null ? geographicLocationService.findById(command.getCreateContactInfoRequest().getParroquia()) : null;

        patientDto.setIdentification(command.getIdentification());
        patientDto.setName(command.getName());
        patientDto.setLastName(command.getLastName());
        patientDto.setGender(command.getGender());
        patientDto.setPhoto(command.getPhoto());
        patientDto.setProfession(command.getProfession());
        patientDto.setEducationalLevel(command.getEducationalLevel());
        patientDto.setBloodType(command.getBloodType());
        patientDto.setDisabilityType(command.getDisabilityType());
        patientDto.setGestationTime(command.getGestationTime());
        patientDto.setSkinColor(command.getSkinColor());
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
        this.patientPublisherService.publishEvent(new RabbitMQPatientDto(
                patientDto.getId(),
                patientDto.getIdentificationType(),
                patientDto.getIdentification(),
                command.getCreateContactInfoRequest().getEmail(),
                patientDto.getName(),
                patientDto.getLastName(),
                patientDto.getPhoto(),
                patientDto.getProfession(),
                patientDto.getStatus().toString(),
                command.getCreateContactInfoRequest().getAddress(),
                command.getCreateContactInfoRequest().getTelephone()


        ));
//        replicatePerson(command, command.getId());
    }

    private void replicatePerson(UpdatePatientsCommand command, UUID id) {
        Person person = new Person();
        person.setId(id.toString());
        person.setIdentificationNumber(command.getIdentification());
        person.setFirstName(command.getName());
        person.setLastName(command.getLastName());
        person.setEmail(command.getCreateContactInfoRequest().getEmail());
        person.setImage(command.getPhoto());
        person.setBirthDate(command.getCreateContactInfoRequest().getBirthdayDate());
        person.setGender(command.getGender().toString());
        person.setPhoneNumber(command.getCreateContactInfoRequest().getTelephone());
        person.setProfession(command.getProfession());

        updatePatientProducer.sendPersonEvent(person);
    }
}
