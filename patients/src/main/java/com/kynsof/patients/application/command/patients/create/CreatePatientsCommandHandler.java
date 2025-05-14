package com.kynsof.patients.application.command.patients.create;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.rules.dependent.DependentMustBeUniqueRule;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDto;
import com.kynsof.patients.infrastructure.services.rabbitMQ.eventPublisher.EventPatientPublisherService;
import com.kynsof.patients.infrastructure.services.rabbitMQ.patientCreate.CreatePatientProducer;
import com.kynsof.patients.infrastructure.services.rabbitMQ.patientCreate.Person;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePatientsCommandHandler implements ICommandHandler<CreatePatientsCommand> {

    private final IPatientsService serviceImpl;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;
    private final CreatePatientProducer createPatientProducer;
    private final EventPatientPublisherService patientPublisherService;

    public CreatePatientsCommandHandler(IPatientsService serviceImpl,
            IContactInfoService contactInfoService,
            IGeographicLocationService geographicLocationService,
            CreatePatientProducer publisher,
            EventPatientPublisherService patientPublisherService
    ) {
        this.serviceImpl = serviceImpl;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
        this.createPatientProducer = publisher;
        this.patientPublisherService = patientPublisherService;
    }

    @Override
    @Transactional
    public void handle(CreatePatientsCommand command) {
        RulesChecker.checkRule(new DependentMustBeUniqueRule(this.serviceImpl, command.getIdentification(), command.getId()));
        GeographicLocationDto parroquia = command.getCreateContactInfoRequest().getParroquia() != null ? geographicLocationService.findById(command.getCreateContactInfoRequest().getParroquia()) : null;
        PatientDto patientDto = new PatientDto(
                command.getId(),
                command.getIdentification(),
                command.getName(),
                command.getLastName(),
                command.getGender(),
                Status.ACTIVE,
                command.getPhoto()
        );
        patientDto.setProfession(command.getProfession());
        patientDto.setEducationalLevel(command.getEducationalLevel());
        patientDto.setBloodType(command.getBloodType());

        UUID id = serviceImpl.create(patientDto);
        command.setId(id);
        patientDto.setId(id);

        try {
            contactInfoService.create(new ContactInfoDto(
                    UUID.randomUUID(),
                    patientDto,
                    command.getCreateContactInfoRequest().getEmail(),
                    command.getCreateContactInfoRequest().getTelephone(),
                    command.getCreateContactInfoRequest().getAddress(),
                    command.getCreateContactInfoRequest().getBirthdayDate(),
                    Status.ACTIVE,
                    parroquia,
                    command.getCreateContactInfoRequest().getConventionalTelephone(),
                    command.getCreateContactInfoRequest().getMaritalStatus()
            ));

        } catch (Exception ignored) {

        }

        this.patientPublisherService.publishEvent(new RabbitMQPatientDto(
                patientDto.getId(),
                patientDto.getIdentification(),
                command.getCreateContactInfoRequest().getEmail(),
                patientDto.getName(),
                patientDto.getLastName(),
                patientDto.getPhoto(),
                patientDto.getProfession(),
                patientDto.getStatus().toString()
        ));
//        replicatePerson(command, id);
    }

    private void replicatePerson(CreatePatientsCommand command, UUID id) {
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

        createPatientProducer.sendPersonEvent(person);
    }
}
