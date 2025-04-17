package com.kynsof.identity.application.command.auth.autenticate;

import com.kynsof.identity.domain.interfaces.service.IAuthService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.AuthenticateNotFoundException;
import com.kynsof.share.core.domain.exception.UserChangePasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticateCommandHandler implements ICommandHandler<AuthenticateCommand> {
    private final IAuthService authService;

    public AuthenticateCommandHandler(IAuthService authService) {
        this.authService = authService;
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
}
