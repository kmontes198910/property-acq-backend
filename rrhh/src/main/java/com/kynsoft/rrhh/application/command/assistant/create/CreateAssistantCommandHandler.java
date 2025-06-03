package com.kynsoft.rrhh.application.command.assistant.create;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsoft.rrhh.domain.dto.AssistantDto;
import com.kynsoft.rrhh.domain.dto.BusinessDto;
import com.kynsoft.rrhh.domain.dto.CreateUserSystemRequest;
import com.kynsoft.rrhh.domain.dto.UserBusinessRelationDto;
import com.kynsoft.rrhh.domain.interfaces.services.IAssistantService;
import com.kynsoft.rrhh.domain.interfaces.services.IBusinessService;
import com.kynsoft.rrhh.domain.interfaces.services.IUserBusinessRelationService;
import com.kynsoft.rrhh.domain.rules.assistant.AssistantEmailMustBeUniqueRule;
import com.kynsoft.rrhh.domain.rules.assistant.AssistantIdentificationMustBeUniqueRule;
import com.kynsoft.rrhh.infrastructure.services.UserSystemService;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.AssistantRabbitMqDto;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher.EventAssistantPublisherService;
import com.kynsoft.rrhh.infrastructure.util.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

// Otros imports

@Slf4j
@Component
public class CreateAssistantCommandHandler implements ICommandHandler<CreateAssistantCommand> {

    private final IAssistantService service;
    private final IBusinessService businessService;
    private final IUserBusinessRelationService userBusinessRelationService;
    private final UserSystemService userSystemService;
    private final EventAssistantPublisherService eventAssistantPublisherService;

    // Inyección de RestTemplate
    public CreateAssistantCommandHandler(IAssistantService service, IBusinessService businessService,
                                         IUserBusinessRelationService userBusinessRelationService, UserSystemService userSystemService,
                                         EventAssistantPublisherService eventAssistantPublisherService) {
        this.service = service;
        this.businessService = businessService;
        this.userBusinessRelationService = userBusinessRelationService;
        this.eventAssistantPublisherService = eventAssistantPublisherService;
        this.userSystemService = userSystemService;
    }

    @Override
    @Transactional
    public void handle(CreateAssistantCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getStatus(), "Assistant.status", "Assistant status cannot be null."));

        RulesChecker.checkRule(new AssistantEmailMustBeUniqueRule(this.service, command.getEmail()));
        RulesChecker.checkRule(new AssistantIdentificationMustBeUniqueRule(this.service, command.getIdentification()));
        BusinessDto businessDto = this.businessService.findById(command.getBusiness());

        try {
            // Consumir el servicio createUserSystem
            log.info("Consumiendo servicio createUserSystem para crear usuario del sistema: {}", command.getEmail());
            var id = consumeCreateUserSystemService(command);
            log.info("Usuario del sistema creado con ID: {}", id);
            AssistantDto assistantSave = new AssistantDto(
                    UUID.fromString(id),
                    command.getIdentification(),
                    command.getCode(),
                    command.getEmail(),
                    command.getName(),
                    command.getLastName(),
                    command.getStatus(),
                    command.getPhoneNumber(),
                    command.getImage(),
                    command.getDepartment()
            );

            service.create(assistantSave);
            log.info("Creando nuevo asistente: {}", assistantSave.getId());

            this.userBusinessRelationService.create(new UserBusinessRelationDto(UUID.randomUUID(),
                    assistantSave, businessDto, "ACTIVE", LocalDateTime.now()));
            log.info("Creando nueva relación de usuario y negocio: {}", assistantSave.getId());
            this.eventAssistantPublisherService.publishEvent(new AssistantRabbitMqDto(
                    assistantSave.getId(),
                    assistantSave.getIdentification(),
                    assistantSave.getName(),
                    assistantSave.getLastName(),
                    "",
                    assistantSave.getStatus(),
                    assistantSave.getImage()
            ));

        } catch (Exception ex) {
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(CreateAssistantCommand command) throws IOException, URISyntaxException, InterruptedException {
        log.info("Iniciando el proceso de creación de usuario del sistema para el asistente: {}", command.getEmail());
        CreateUserSystemRequest createUserSystemRequest = new CreateUserSystemRequest();
        createUserSystemRequest.setUserName(command.getEmail());
        createUserSystemRequest.setEmail(command.getEmail());
        createUserSystemRequest.setName(command.getName());
        createUserSystemRequest.setLastName(command.getLastName());
        createUserSystemRequest.setPassword(PasswordGenerator.generatePassword()); // Ajusta según tus necesidades
        createUserSystemRequest.setUserType(EUserType.ASSISTANTS); // Ajusta si es necesario
        createUserSystemRequest.setImage(command.getImage());
        createUserSystemRequest.setBusinessId(command.getBusiness().toString());

        log.info("Usuario del sistema creado exitosamente para el asistente: {}", command.getEmail());
        return userSystemService.createUserSystem(createUserSystemRequest);
    }

}