package com.kynsof.patients.application.command.patients.create;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.rules.dependent.DependentMustBeUniqueRule;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.services.kafka.producer.customer.ProducerCreateCustomerEventService;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.kafka.entity.CustomerKafka;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreatePatientsCommandHandler implements ICommandHandler<CreatePatientsCommand> {

    private final IPatientsService serviceImpl;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;

    private final ProducerCreateCustomerEventService createCustomerEventService;

    public CreatePatientsCommandHandler(IPatientsService serviceImpl, IContactInfoService contactInfoService,
                                        IGeographicLocationService geographicLocationService,
                                        ProducerCreateCustomerEventService createCustomerEventService
    ) {
        this.serviceImpl = serviceImpl;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
        this.createCustomerEventService = createCustomerEventService;
    }

    @Override
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
                command.getPhoto());

        UUID id = serviceImpl.create(patientDto);
        command.setId(id);
        patientDto.setId(id);

        contactInfoService.create(new ContactInfoDto(
                UUID.randomUUID(),
                patientDto,
                command.getCreateContactInfoRequest().getEmail(),
                command.getCreateContactInfoRequest().getTelephone(),
                command.getCreateContactInfoRequest().getAddress(),
                command.getCreateContactInfoRequest().getBirthdayDate(),
                Status.ACTIVE,
                parroquia
        ));

        this.createCustomerEventService.create(new CustomerKafka(
                patientDto.getId().toString(),
                patientDto.getIdentification(),
                patientDto.getName(),
                patientDto.getLastName(),
                command.getCreateContactInfoRequest().getEmail(),
                patientDto.getPhoto(),
                command.getCreateContactInfoRequest().getBirthdayDate(),
                patientDto.getGender().name(),
                patientDto.getContactInfo().getTelephone()
        ));
    }
}
