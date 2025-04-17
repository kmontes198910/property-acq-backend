package com.kynsoft.rrhh.application.command.doctor.create;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsoft.rrhh.domain.dto.BusinessDto;
import com.kynsoft.rrhh.domain.dto.CreateUserSystemRequest;
import com.kynsoft.rrhh.domain.dto.DoctorDto;
import com.kynsoft.rrhh.domain.dto.UserBusinessRelationDto;
import com.kynsoft.rrhh.domain.interfaces.services.IBusinessService;
import com.kynsoft.rrhh.domain.interfaces.services.IDoctorService;
import com.kynsoft.rrhh.domain.interfaces.services.IUserBusinessRelationService;
import com.kynsoft.rrhh.domain.rules.doctor.DoctorCodeMustBeUniqueRule;
import com.kynsoft.rrhh.domain.rules.doctor.DoctorEmailMustBeUniqueRule;
import com.kynsoft.rrhh.domain.rules.doctor.DoctorIdentificationMustBeUniqueRule;
import com.kynsoft.rrhh.domain.rules.users.UserSystemEmailValidateRule;
import com.kynsoft.rrhh.infrastructure.services.UserSystemService;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.DoctorRabbitMqDto;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher.EventDoctorPublisherService;
import com.kynsoft.rrhh.infrastructure.util.PasswordGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateDoctorCommandHandler implements ICommandHandler<CreateDoctorCommand> {

    private final IDoctorService service;
    private final IBusinessService businessService;
    private final IUserBusinessRelationService userBusinessRelationService;
    private final UserSystemService userSystemService;
    private final EventDoctorPublisherService eventDoctorPublisherService;

    public CreateDoctorCommandHandler(IDoctorService service, IBusinessService businessService,
                                      IUserBusinessRelationService userBusinessRelationService,
                                      UserSystemService userSystemService,
                                      EventDoctorPublisherService eventDoctorPublisherService) {
        this.service = service;
        this.businessService = businessService;
        this.userBusinessRelationService = userBusinessRelationService;
        this.userSystemService = userSystemService;
        this.eventDoctorPublisherService = eventDoctorPublisherService;
    }

    @Override
    @Transactional
    public void handle(CreateDoctorCommand command) {
        RulesChecker.checkRule(new UserSystemEmailValidateRule(command.getEmail()));
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getStatus(), "Doctor.status", "Doctor status cannot be null."));
        RulesChecker.checkRule(new DoctorEmailMustBeUniqueRule(this.service, command.getEmail()));
        RulesChecker.checkRule(new DoctorIdentificationMustBeUniqueRule(this.service, command.getIdentification()));
        RulesChecker.checkRule(new DoctorCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));

        BusinessDto businessDto = this.businessService.findById(command.getBusiness());
        DoctorDto doctorSave = new DoctorDto(
                command.getId(),
                command.getIdentification(),
                command.getCode(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                command.getStatus(),
                command.getRegisterNumber(),
                command.getLanguage(),
                command.isExpress(),
                command.getPhoneNumber(),
                command.getImage()
        );

        try {
            String id = consumeCreateUserSystemService(command);
            command.setId(UUID.fromString(id));
            doctorSave.setId(UUID.fromString(id));
            service.create(doctorSave);
            this.userBusinessRelationService.create(new UserBusinessRelationDto(UUID.randomUUID(),
                    doctorSave,businessDto, "ACTIVE", LocalDateTime.now()));

            this.eventDoctorPublisherService.publishEvent(new DoctorRabbitMqDto(
                    doctorSave.getId(), 
                    doctorSave.getIdentification(), 
                    doctorSave.getName(), 
                    doctorSave.getLastName(), 
                    doctorSave.getRegisterNumber(), 
                    doctorSave.getStatus(), 
                    doctorSave.getImage()
            ));
        }catch (Exception exception){
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(CreateDoctorCommand command) throws IOException, URISyntaxException, InterruptedException {
        CreateUserSystemRequest createUserSystemRequest = new CreateUserSystemRequest();
        createUserSystemRequest.setUserName(command.getEmail());
        createUserSystemRequest.setEmail(command.getEmail());
        createUserSystemRequest.setName(command.getName());
        createUserSystemRequest.setLastName(command.getLastName());
        createUserSystemRequest.setPassword(PasswordGenerator.generatePassword()); // Ajusta según tus necesidades
        createUserSystemRequest.setUserType(EUserType.DOCTORS); // Ajusta si es necesario
        createUserSystemRequest.setImage(command.getImage());
        createUserSystemRequest.setBusinessId(command.getBusiness().toString());
        return userSystemService.createUserSystem(createUserSystemRequest);

    }
}