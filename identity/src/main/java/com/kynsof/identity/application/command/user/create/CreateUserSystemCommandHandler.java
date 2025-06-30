package com.kynsof.identity.application.command.user.create;

import com.kynsof.identity.application.command.auth.registrySystemUser.UserSystemKycloackRequest;
import com.kynsof.identity.domain.dto.BusinessDto;
import com.kynsof.identity.domain.dto.UserPermissionBusinessDto;
import com.kynsof.identity.domain.dto.UserStatus;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.dto.mailjet.MailJetRecipientDto;
import com.kynsof.identity.domain.dto.mailjet.MailJetVarDto;
import com.kynsof.identity.domain.dto.mailjet.SendMailJetEmailRequestDto;
import com.kynsof.identity.domain.interfaces.service.*;
import com.kynsof.identity.domain.rules.usersystem.ModuleEmailMustBeUniqueRule;
import com.kynsof.identity.domain.rules.usersystem.ModuleUserNameMustBeUniqueRule;
import com.kynsof.identity.infrastructure.entities.Permission;
import com.kynsof.identity.infrastructure.entities.UserTypePermission;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
@Slf4j
public class CreateUserSystemCommandHandler implements ICommandHandler<CreateUserSystemCommand> {

    private final IUserSystemService userSystemService;
    private final IAuthService authService;
    private final IUserPermissionBusinessService service;
    private final IBusinessService businessService;
    private final IUserTypePermissionService userTypePermissionService;
    private final ICloudBridgesFileService cloudBridgesService;

    @Autowired
    public CreateUserSystemCommandHandler(IUserSystemService userSystemService, IAuthService authService,
                                          IUserPermissionBusinessService service,
                                          IBusinessService businessService,
                                          IUserTypePermissionService userTypePermissionService,
                                          ICloudBridgesFileService cloudBridgesService) {
        this.userSystemService = userSystemService;
        this.authService = authService;
        this.service = service;
        this.businessService = businessService;
        this.userTypePermissionService = userTypePermissionService;
        this.cloudBridgesService = cloudBridgesService;
    }

    @Override
    public void handle(CreateUserSystemCommand command) {
        log.error("Creating user system with command: {}", command);
        RulesChecker.checkRule(new ModuleEmailMustBeUniqueRule(this.userSystemService, command.getEmail(), UUID.randomUUID()));
        RulesChecker.checkRule(new ModuleUserNameMustBeUniqueRule(this.userSystemService, command.getUserName(), UUID.randomUUID()));

        UserSystemKycloackRequest userSystemRequest = new UserSystemKycloackRequest(
                command.getUserName(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                command.getPassword(),
                command.getUserType()
        );
        String userId = authService.registerUserSystem(userSystemRequest, true);

        UserSystemDto userDto = new UserSystemDto(
                command.getId(),
                command.getUserName(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                UserStatus.ACTIVE,
                command.getImage()
        );
        userDto.setKeyCloakId(UUID.fromString(userId));
        userDto.setUserName(command.getUserName());
        userDto.setUserType(command.getUserType());

        UUID id = userSystemService.create(userDto);
        command.setId(id);
        if (command.getBusinessId() != null) {
            addPermission(command,userDto);
        }

        log.info("User system created with ID: {}", id);


        sendEmail(command);
    }

    private void sendEmail(CreateUserSystemCommand command) {
        try {
            // Crear el objeto de solicitud
            SendMailJetEmailRequestDto requestDto = new SendMailJetEmailRequestDto();

            // Configurar destinatario
            List<MailJetRecipientDto> recipients = new ArrayList<>();
            recipients.add(new MailJetRecipientDto(command.getEmail(), command.getLastName() + " " + command.getName()));
            requestDto.setRecipientEmail(recipients);
            LocalDate issueDate = LocalDate.now(); // o tu fecha específica


            // Configurar variables para la plantilla
            List<MailJetVarDto> vars = new ArrayList<>();
            vars.add(new MailJetVarDto("user_name", command.getEmail()));
            vars.add(new MailJetVarDto("temp_password", command.getPassword()));

            requestDto.setMailJetVars(vars);

            // Configurar el asunto
            requestDto.setSubject("Bienvenido a Kynsoft - Usuario creado");

            // ID de la plantilla en Mailjet (este es un ejemplo, debe configurarse el ID correcto)
            requestDto.setTemplateId("5965446");

            // Enviar la solicitud
           cloudBridgesService.sendEmail(requestDto);

        } catch (Exception e) {
      log.error("Error al enviar el correo de bienvenida: {}", e.getMessage(), e);
            // Manejar el error de envío de correo
            throw new RuntimeException("Error al enviar el correo de bienvenida: " + e.getMessage());
        }
    }

    private void addPermission(CreateUserSystemCommand command,  UserSystemDto userSystemDto) {
        List<UserPermissionBusinessDto> userRoleBusinessDtos = new ArrayList<>();
        BusinessDto businessDto = this.businessService.findById(UUID.fromString(command.getBusinessId()));
        List< UserTypePermission> userTypePermissions = userTypePermissionService.getPermissionsByUserType(command.getUserType());
        List<Permission> permissions = userTypePermissions.stream().map(UserTypePermission::getPermission).toList();
        service.delete(UUID.fromString(command.getBusinessId()), userSystemDto.getId());
        for (Permission permission : permissions) {

            userRoleBusinessDtos.add(new UserPermissionBusinessDto(UUID.randomUUID(), userSystemDto, permission.toAggregate(), businessDto));
        }
        this.service.create(userRoleBusinessDtos);
    }
}
