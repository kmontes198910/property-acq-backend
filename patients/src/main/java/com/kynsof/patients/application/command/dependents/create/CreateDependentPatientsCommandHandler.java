package com.kynsof.patients.application.command.dependents.create;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.DependentPatientDto;
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
public class CreateDependentPatientsCommandHandler implements ICommandHandler<CreateDependentPatientsCommand> {

    private final IPatientsService serviceImpl;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;

    private final ProducerCreateCustomerEventService createCustomerEventService;

    public CreateDependentPatientsCommandHandler(IPatientsService serviceImpl, IContactInfoService contactInfoService,
            IGeographicLocationService geographicLocationService,
            ProducerCreateCustomerEventService createCustomerEventService
    ) {
        this.serviceImpl = serviceImpl;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
        this.createCustomerEventService = createCustomerEventService;
    }

    @Override
    public void handle(CreateDependentPatientsCommand command) {

        PatientDto prime = serviceImpl.findByIdSimple(command.getPrimeId());

        UUID idDependent = UUID.randomUUID();
        RulesChecker.checkRule(new DependentMustBeUniqueRule(this.serviceImpl, command.getIdentification(), idDependent));
        DependentPatientDto dependentPatientDto = new DependentPatientDto(
                idDependent,
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
        UUID id = serviceImpl.createDependent(dependentPatientDto);
        PatientDto patientDto = serviceImpl.findByIdSimple(id);
        GeographicLocationDto parroquia = geographicLocationService.findById(command.getCreateContactInfoRequest().getParroquia());
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
        command.setId(id);

        this.createCustomerEventService.create(new CustomerKafka(
                id.toString(),
                dependentPatientDto.getIdentification(),
                dependentPatientDto.getName(),
                dependentPatientDto.getLastName(),
                command.getCreateContactInfoRequest().getEmail(),
                command.getPhoto(),
                command.getCreateContactInfoRequest().getBirthdayDate(),
                dependentPatientDto.getGender().name(),
                command.getCreateContactInfoRequest().getTelephone()
        ));
    }
}
