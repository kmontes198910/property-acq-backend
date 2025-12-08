package com.kynsof.identity.controller;

import com.kynsof.identity.application.command.auth.TokenRefreshRequest;
import com.kynsof.identity.application.command.auth.autenticate.*;
import com.kynsof.identity.application.command.auth.deletedAccount.DeleteAccountCommand;
import com.kynsof.identity.application.command.auth.deletedAccount.DeleteAccountMessage;
import com.kynsof.identity.application.command.auth.firstsChangePassword.FirstsChangePasswordCommand;
import com.kynsof.identity.application.command.auth.firstsChangePassword.FirstsChangePasswordMessage;
import com.kynsof.identity.application.command.auth.firstsChangePassword.FirstsChangePasswordRequest;
import com.kynsof.identity.application.command.auth.forwardPassword.ForwardPasswordCommand;
import com.kynsof.identity.application.command.auth.forwardPassword.ForwardPasswordMessage;
import com.kynsof.identity.application.command.auth.forwardPassword.PasswordChangeRequest;
import com.kynsof.identity.application.command.auth.registry.RegistryCommand;
import com.kynsof.identity.application.command.auth.registry.RegistryMessage;
import com.kynsof.identity.application.command.auth.registry.UserRequest;
import com.kynsof.identity.application.command.auth.sendPasswordRecoveryOtp.SendPasswordRecoveryOtpCommand;
import com.kynsof.identity.application.command.auth.sendPasswordRecoveryOtp.SendPasswordRecoveryOtpMessage;
import com.kynsof.identity.application.query.auth.RefreshTokenQuery;
import com.kynsof.identity.application.query.users.existByEmail.ExistByEmailUserSystemsQuery;
import com.kynsof.identity.application.query.users.existByEmail.UserSystemsExistByEmailResponse;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar autenticación y operaciones relacionadas con la identidad de usuario.
 * Implementa rate limiting consistente para proteger los endpoints sensibles contra ataques de fuerza bruta.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IMediator mediator;
    
    @Value("${app.version}")
    private String appVersion;

    public AuthController(IMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Endpoint para autenticación de usuarios.
     * Protegido con rate limiting para prevenir ataques de fuerza bruta.
     */
    @RateLimiter(name = "authenticationLimit", fallbackMethod = "authenticateFallback")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginDTO) {
        AuthenticateCommand authenticateCommand = new AuthenticateCommand(loginDTO.getUsername(), loginDTO.getPassword());
        AuthenticateMessage response = mediator.send(authenticateCommand);

        // Verificar si hay errores de autenticación
        TokenResponse tokenResponse = response.getTokenResponse();
        if (tokenResponse.getError() != null) {
            // Si hay error, devolver respuesta de error en el formato estandarizado
            ApiError apiError = ApiError.withSingleError(
                    tokenResponse.getErrorDescription(),
                    tokenResponse.getErrorField(),
                    tokenResponse.getErrorMessage()
            );
            if( "password_change_required".equals(tokenResponse.getError())) {
                // Si el error es de cambio de contraseña, devolver un código 412
                return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED)
                        .body(ApiResponse.fail(apiError));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.fail(apiError));
        }

        // Si no hay error, devolver directamente la estructura del token
        // sin encapsularla en un ApiResponse
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * Método fallback para el endpoint de autenticación cuando se excede el rate limit.
     */
    public ResponseEntity<?> authenticateFallback(LoginRequest request, RequestNotPermitted ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "60");
        
        ApiError apiError = new ApiError("Demasiados intentos de inicio de sesión. Por favor, inténtelo más tarde.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body(ApiResponse.fail(apiError));
    }

    /**
     * Endpoint para eliminar cuenta de usuario.
     * Protegido con rate limiting para prevenir ataques de eliminación masiva.
     */
    @RateLimiter(name = "accountOperationsLimit", fallbackMethod = "accountOperationsFallback")
    @PostMapping("/delete-account")
    public ResponseEntity<ApiResponse<?>> deleteAccount(@RequestBody DeleteRequest loginDTO) {
        DeleteAccountCommand command = new DeleteAccountCommand(loginDTO.getUsername(), loginDTO.getPassword());
        DeleteAccountMessage response = this.mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(response.getResult()));
    }

    /**
     * Endpoint para el primer cambio de contraseña (generalmente después de registro).
     * Protegido con rate limiting específico para cambios de contraseña.
     */
    @PreAuthorize("permitAll()")
    @RateLimiter(name = "passwordChangeLimit", fallbackMethod = "passwordOperationsFallback")
    @PostMapping("/firsts-change-password")
    public ResponseEntity<?> firstsChangePassword(@RequestBody FirstsChangePasswordRequest request) {
        FirstsChangePasswordCommand authenticateCommand = FirstsChangePasswordCommand.fromRequest(request);
        FirstsChangePasswordMessage response = mediator.send(authenticateCommand);
        return ResponseEntity.ok(response.getResult());
    }

    /**
     * Endpoint para registro de nuevos usuarios.
     * Protegido con rate limiting para prevenir registros masivos automatizados.
     */
    @PreAuthorize("permitAll()")
    @RateLimiter(name = "registrationLimit", fallbackMethod = "registrationFallback")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserRequest userRequest) {
        RegistryCommand command = new RegistryCommand(userRequest.getUserName(), userRequest.getEmail(), userRequest.getName(),
                userRequest.getLastName(), userRequest.getPassword(), null);
        RegistryMessage registryMessage = mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(registryMessage.getResult()));
    }

    /**
     * Endpoint para refrescar tokens de autenticación.
     * Protegido con rate limiting para prevenir abusos.
     */
    @RateLimiter(name = "authenticationLimit", fallbackMethod = "tokenOperationsFallback")
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody TokenRefreshRequest request) {
        RefreshTokenQuery query = new RefreshTokenQuery(request.getRefreshToken());
        TokenResponse response = mediator.send(query);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Endpoint para recuperación de contraseña olvidada.
     * Protegido con rate limiting para prevenir ataques de enumeración de usuarios.
     */
    @RateLimiter(name = "passwordRecoveryLimit", fallbackMethod = "passwordOperationsFallback")
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestParam String email) {
        SendPasswordRecoveryOtpCommand command = new SendPasswordRecoveryOtpCommand(email);
        SendPasswordRecoveryOtpMessage sendPasswordRecoveryOtpMessage = mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(sendPasswordRecoveryOtpMessage.getResult()));
    }

    /**
     * Endpoint para cambio de contraseña.
     * Protegido con rate limiting específico para cambios de contraseña.
     */
    @RateLimiter(name = "passwordChangeLimit", fallbackMethod = "passwordOperationsFallback")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody PasswordChangeRequest request) {
        ForwardPasswordCommand command = new ForwardPasswordCommand(request.getEmail(), request.getNewPassword(),
                request.getOtp());
        ForwardPasswordMessage response = mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(response.getResult()));
    }
    /**
     * Endpoint para verificar la versión de la aplicación.
     * No requiere rate limiting al ser información no sensible.
     */
    @GetMapping("/app-version")
    public ResponseEntity<?> appVersion() {
        return ResponseEntity.ok(ApiResponse.success(appVersion));
    }

    /**
     * Endpoint para verificar la existencia de un usuario por email.
     * Protegido con rate limiting para prevenir ataques de enumeración de usuarios.
     */
    @RateLimiter(name = "emailCheckLimit", fallbackMethod = "emailCheckFallback")
    @GetMapping("/exist-by-email/{email}")
    public ResponseEntity<UserSystemsExistByEmailResponse> existUserByEmail(@PathVariable String email) {
        ExistByEmailUserSystemsQuery query = new ExistByEmailUserSystemsQuery(email);
        UserSystemsExistByEmailResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Métodos fallback para diferentes operaciones cuando se exceden los rate limits
     */
    public ResponseEntity<?> accountOperationsFallback(DeleteRequest request, RequestNotPermitted ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "300");
        
        ApiError apiError = new ApiError("Demasiados intentos de operaciones con la cuenta. Por favor, inténtelo más tarde.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body(ApiResponse.fail(apiError));
    }
    
    public ResponseEntity<?> passwordOperationsFallback(Object request, RequestNotPermitted ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "300");
        
        ApiError apiError = new ApiError("Demasiados intentos de operaciones con contraseñas. Por favor, inténtelo más tarde.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body(ApiResponse.fail(apiError));
    }
    
    public ResponseEntity<?> registrationFallback(UserRequest request, RequestNotPermitted ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "3600");
        
        ApiError apiError = new ApiError("Demasiados intentos de registro. Por favor, inténtelo más tarde.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body(ApiResponse.fail(apiError));
    }
    
    public ResponseEntity<?> tokenOperationsFallback(TokenRefreshRequest request, RequestNotPermitted ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "60");
        
        ApiError apiError = new ApiError("Demasiados intentos de operaciones con tokens. Por favor, inténtelo más tarde.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body(ApiResponse.fail(apiError));
    }
    
    public ResponseEntity<?> emailCheckFallback(String email, RequestNotPermitted ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "60");
        
        ApiError apiError = new ApiError("Demasiadas verificaciones de email. Por favor, inténtelo más tarde.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body(ApiResponse.fail(apiError));
    }
}
