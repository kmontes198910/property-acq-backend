package com.kynsoft.rrhh.application.command.nurse.create;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsoft.rrhh.domain.dto.BusinessDto;
import com.kynsoft.rrhh.domain.dto.CreateUserSystemRequest;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import com.kynsoft.rrhh.domain.dto.UserBusinessRelationDto;
import com.kynsoft.rrhh.domain.interfaces.services.IBusinessService;
import com.kynsoft.rrhh.domain.interfaces.services.INurseService;
import com.kynsoft.rrhh.domain.interfaces.services.IUserBusinessRelationService;

import com.kynsoft.rrhh.domain.rules.users.UserSystemEmailValidateRule;
import com.kynsoft.rrhh.infrastructure.services.UserSystemService;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.NurseRabbitMqDto;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher.EventNursePublisherService;
import com.kynsoft.rrhh.infrastructure.util.PasswordGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CreateNurseCommandHandler implements ICommandHandler<CreateNurseCommand> {

    private final INurseService service;
    private final IBusinessService businessService;
    private final IUserBusinessRelationService userBusinessRelationService;
    private final UserSystemService userSystemService;
    private final EventNursePublisherService eventNursePublisherService;

    public CreateNurseCommandHandler(INurseService service, IBusinessService businessService,
                                    IUserBusinessRelationService userBusinessRelationService,
                                    UserSystemService userSystemService,
                                    EventNursePublisherService eventNursePublisherService) {
        this.service = service;
        this.businessService = businessService;
        this.userBusinessRelationService = userBusinessRelationService;
        this.userSystemService = userSystemService;
        this.eventNursePublisherService = eventNursePublisherService;
    }

    @Override
    @Transactional
    public void handle(CreateNurseCommand command) {
        RulesChecker.checkRule(new UserSystemEmailValidateRule(command.getEmail()));
        BusinessDto businessDto = this.businessService.findById(command.getBusiness());
        NurseDto nurseSave = new NurseDto(
                command.getId(),
                command.getIdentification(),
                command.getCode(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                "ACTIVE",
                command.getRegisterNumber(),
                command.getLanguage(),
                command.isExpress(),
                command.getClasificacion(),
                command.getPhoneNumber(),
                command.getImage()
        );

        try {
            String id = consumeCreateUserSystemService(command);
            command.setId(UUID.fromString(id));
            nurseSave.setId(UUID.fromString(id));
            service.create(nurseSave);
            this.userBusinessRelationService.create(new UserBusinessRelationDto(UUID.randomUUID(),
                    nurseSave, businessDto, "ACTIVE", LocalDateTime.now()));

            this.eventNursePublisherService.publishEvent(new NurseRabbitMqDto(
                    nurseSave.getId(), 
                    nurseSave.getIdentification(), 
                    nurseSave.getName(), 
                    nurseSave.getLastName(), 
                    nurseSave.getRegisterNumber(), 
                    nurseSave.getStatus(), 
                    nurseSave.getImage()
            ));
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(CreateNurseCommand command) throws IOException, URISyntaxException, InterruptedException {
        CreateUserSystemRequest createUserSystemRequest = new CreateUserSystemRequest();
        createUserSystemRequest.setUserName(command.getEmail());
        createUserSystemRequest.setEmail(command.getEmail());
        createUserSystemRequest.setName(command.getName());
        createUserSystemRequest.setLastName(command.getLastName());
        createUserSystemRequest.setPassword(PasswordGenerator.generatePassword());
        createUserSystemRequest.setUserType(EUserType.NURSES);
        createUserSystemRequest.setImage(command.getImage());
        createUserSystemRequest.setBusinessId(command.getBusiness().toString());
        return userSystemService.createUserSystem(createUserSystemRequest);
    }
}