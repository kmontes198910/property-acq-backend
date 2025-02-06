package com.kynsof.identity.application.command.auth.deletedAccount;


import com.kynsof.identity.application.command.auth.autenticate.LoginRequest;
import com.kynsof.identity.application.command.auth.autenticate.TokenResponse;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IAuthService;
import com.kynsof.identity.domain.interfaces.service.IRedisService;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeletedAccountCommandHandler implements ICommandHandler<DeleteAccountCommand> {
    private final IAuthService authService;
    private final IUserSystemService userSystemService;
    private final IRedisService otpService;

    public DeletedAccountCommandHandler(IAuthService authService, IUserSystemService userSystemService, IRedisService otpService) {

        this.authService = authService;
        this.userSystemService = userSystemService;
        this.otpService = otpService;
    }

    @Override
    public void handle(DeleteAccountCommand command) {
        TokenResponse tokenResponse = authService.authenticate(new LoginRequest(command.getEmail(), command.getPassword()));
        UserSystemDto userSystemDto = userSystemService.findByEmail(command.getEmail());
        if (userSystemDto != null) {
            Boolean result = authService.delete(userSystemDto.getId().toString());
            command.setResul(result);
        }

    }
}
