package com.kynsof.identity.application.command.firebaseToken.update;

import com.kynsof.identity.domain.dto.FirebaseTokenDto;
import com.kynsof.identity.domain.interfaces.service.IFirebaseTokenService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateFirebaseTokenCommandHandler implements ICommandHandler<UpdateFirebaseTokenCommand> {

    private final IFirebaseTokenService service;

    public UpdateFirebaseTokenCommandHandler(IFirebaseTokenService service) {
        this.service = service;
    }

    @Override
    public void handle(UpdateFirebaseTokenCommand command) {

        FirebaseTokenDto firebaseTokenDto = service.findById(command.getId());
        firebaseTokenDto.setToken(command.getToken());
        service.update(firebaseTokenDto);

    }
}
