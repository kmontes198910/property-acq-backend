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
import com.kynsof.share.core.infrastructure.bus.IMediator;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IMediator mediator;
    @Value("${app.version}")
    private String appVersion;

    public AuthController(IMediator mediator) {

        this.mediator = mediator;
    }
//    @PreAuthorize("permitAll()")
//    //@RateLimiter(name = "emailRateLimit")
//    @PostMapping("/authenticate")
//    public Mono<ResponseEntity<TokenResponse>> authenticate(@RequestBody LoginRequest loginDTO) {
//        AuthenticateCommand authenticateCommand = new AuthenticateCommand(loginDTO.getUsername(), loginDTO.getPassword());
//        AuthenticateMessage response = mediator.send(authenticateCommand);
//        return Mono.just(ResponseEntity.ok(response.getTokenResponse()));
//    }

    @RateLimiter(name = "emailRateLimit", fallbackMethod = "authenticateFallback")
    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest loginDTO) {
        AuthenticateCommand authenticateCommand = new AuthenticateCommand(loginDTO.getUsername(), loginDTO.getPassword());
        AuthenticateMessage response = mediator.send(authenticateCommand);
        return ResponseEntity.ok(response.getTokenResponse());
    }

    public ResponseEntity<?> authenticateFallback(LoginRequest request, Throwable t) {

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many request - No further calls are accepted");
    }

    //@PreAuthorize("permitAll()")
    @PostMapping("/delete-account")
    public ResponseEntity<ApiResponse<?>> deleteAccount(@RequestBody DeleteRequest loginDTO){
        DeleteAccountCommand command = new DeleteAccountCommand(loginDTO.getUsername(), loginDTO.getPassword());
        DeleteAccountMessage response = this.mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(response.getResult()));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/firsts-change-password")
  //  @RateLimit(type = RateLimit.RateLimitType.PASSWORD_CHANGE)
    public Mono<ResponseEntity<?>> firstsChangePassword(@RequestBody FirstsChangePasswordRequest request) {
        FirstsChangePasswordCommand authenticateCommand = FirstsChangePasswordCommand.fromRequest(request);
        FirstsChangePasswordMessage response = mediator.send(authenticateCommand);
        return Mono.just(ResponseEntity.ok(response.getResult()));
    }

    // @PreAuthorize("permitAll()")
    @PostMapping("/register")
  //  @RateLimit(type = RateLimit.RateLimitType.DEFAULT)
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserRequest userRequest) {
        RegistryCommand command = new RegistryCommand(userRequest.getUserName(), userRequest.getEmail(), userRequest.getName(),
                userRequest.getLastName(), userRequest.getPassword(), null);
        RegistryMessage registryMessage = mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(registryMessage.getResult()));
    }


    @PostMapping("/refresh-token")
    //   @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody TokenRefreshRequest request) {
        RefreshTokenQuery query = new RefreshTokenQuery(request.getRefreshToken());
        TokenResponse response = mediator.send(query);
        return ResponseEntity.ok(ApiResponse.success(response));

    }

    @PostMapping("/forgot-password")
  //  @RateLimit(type = RateLimit.RateLimitType.PASSWORD_RECOVERY)
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestParam String email) {
        SendPasswordRecoveryOtpCommand command = new SendPasswordRecoveryOtpCommand(email);
        SendPasswordRecoveryOtpMessage sendPasswordRecoveryOtpMessage = mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(sendPasswordRecoveryOtpMessage.getResult()));
    }

    @PostMapping("/change-password")
  //  @RateLimit(type = RateLimit.RateLimitType.PASSWORD_CHANGE)
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody PasswordChangeRequest request) {
        ForwardPasswordCommand command = new ForwardPasswordCommand(request.getEmail(), request.getNewPassword(),
                request.getOtp());
        ForwardPasswordMessage response = mediator.send(command);
        return ResponseEntity.ok(ApiResponse.success(response.getResult()));
    }

    @GetMapping("/app-version")
    public ResponseEntity<?> appVersion() {
        return ResponseEntity.ok(ApiResponse.success(appVersion));
    }

    @GetMapping("/exist-by-email/{email}")
    @RateLimiter(name = "emailRateLimit", fallbackMethod = "greetingFallBack")
    //@RateLimit(type = RateLimit.RateLimitType.DEFAULT)
    public ResponseEntity<UserSystemsExistByEmailResponse> existUserByEmail(@PathVariable String email) {
        System.out.println("Rate limit accepted");
        ExistByEmailUserSystemsQuery query = new ExistByEmailUserSystemsQuery(email);
        UserSystemsExistByEmailResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
    public ResponseEntity greetingFallBack(String name, io.github.resilience4j.ratelimiter.RequestNotPermitted ex) {
        System.out.println("Rate limit applied no further calls are accepted");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "1"); //retry after one second

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders) //send retry header
                .body("Too many request - No further calls are accepted");
    }
}
