package com.kynsof.identity.application.command.auth.autenticate;

import com.kynsof.identity.application.command.user.create.CreateUserSystemCommand;
import com.kynsof.identity.domain.dto.mailjet.MailJetRecipientDto;
import com.kynsof.identity.domain.dto.mailjet.MailJetVarDto;
import com.kynsof.identity.domain.dto.mailjet.SendMailJetEmailRequestDto;
import com.kynsof.identity.domain.interfaces.service.IAuthService;
import com.kynsof.identity.domain.interfaces.service.ICloudBridgesFileService;
import com.kynsof.identity.infrastructure.services.RedisOtpService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.AuthenticateNotFoundException;
import com.kynsof.share.core.domain.exception.UserChangePasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AuthenticateCommandHandler implements ICommandHandler<AuthenticateCommand> {
    private final IAuthService authService;
    private final ICloudBridgesFileService cloudBridgesService;
    private final RedisOtpService redisOtpService;

    public AuthenticateCommandHandler(IAuthService authService, ICloudBridgesFileService cloudBridgesService, RedisOtpService redisOtpService) {
        this.authService = authService;
        this.cloudBridgesService = cloudBridgesService;
        this.redisOtpService = redisOtpService;
    }

    @Override
    public void handle(AuthenticateCommand command) {
        try {
            LoginRequest loginRequest = new LoginRequest(command.getUserName(), command.getPassword());
            TokenResponse tokenResponse = authService.authenticate(loginRequest);
            command.setTokenResponse(tokenResponse);
        } catch (AuthenticateNotFoundException ex) {
            log.warn("Intento de autenticación fallido para el usuario: {}", command.getUserName());
            // Crear respuesta de error en formato estandarizado
            TokenResponse errorResponse = new TokenResponse();
            errorResponse.setError("invalid_credentials");
            errorResponse.setErrorDescription("El usuario o contraseña es incorrecto. Por favor intente de nuevo.");
            errorResponse.setAuthenticated(false);
            errorResponse.setErrorField("email/password");
            errorResponse.setErrorMessage("The username or password is incorrect. Please try again.");
            command.setTokenResponse(errorResponse);
        } catch (UserChangePasswordException ex) {
            log.info("Usuario debe cambiar contraseña: {}", command.getUserName());
            // Respuesta específica para cambio de contraseña obligatorio
            TokenResponse errorResponse = new TokenResponse();
            errorResponse.setError("password_change_required");
            errorResponse.setErrorDescription("Debe cambiar su contraseña antes de continuar.");
            errorResponse.setAuthenticated(false);
            errorResponse.setErrorField("password");
            errorResponse.setErrorMessage("You must change your password before continuing.");
            command.setTokenResponse(errorResponse);
            sendEmail(command);
        } catch (Exception ex) {
            log.error("Error inesperado durante la autenticación: ", ex);
            // Respuesta para errores inesperados
            TokenResponse errorResponse = new TokenResponse();
            errorResponse.setError("authentication_error");
            errorResponse.setErrorDescription("Error al procesar la solicitud de autenticación. Por favor, inténtelo más tarde.");
            errorResponse.setAuthenticated(false);
            errorResponse.setErrorField("system");
            errorResponse.setErrorMessage("An unexpected error occurred. Please try again later.");
            command.setTokenResponse(errorResponse);
        }
    }

    private void sendEmail(AuthenticateCommand command) {
        try {
            // Crear el objeto de solicitud
            SendMailJetEmailRequestDto requestDto = new SendMailJetEmailRequestDto();

            // Configurar destinatario
            List<MailJetRecipientDto> recipients = new ArrayList<>();
            recipients.add(new MailJetRecipientDto(command.getUserName(), "Usuario "));
            requestDto.setRecipientEmail(recipients);

            // Configurar variables para la plantilla
            List<MailJetVarDto> vars = new ArrayList<>();
          String otp =  redisOtpService.generateOtpCode();
          redisOtpService.saveOtpCode(command.getUserName(), otp);
            vars.add(new MailJetVarDto("otp_token", otp));

            requestDto.setMailJetVars(vars);

            // Configurar el asunto
            requestDto.setSubject("OTP de autenticación");

            // ID de la plantilla en Mailjet (este es un ejemplo, debe configurarse el ID correcto)
            requestDto.setTemplateId("5964805");

            // Enviar la solicitud
            cloudBridgesService.sendEmail(requestDto);

        } catch (Exception e) {
            log.error("Error al enviar el correo de bienvenida: {}", e.getMessage(), e);
            // Manejar el error de envío de correo
            throw new RuntimeException("Error al enviar el correo de bienvenida: " + e.getMessage());
        }
    }
}
