package com.kynsof.identity.application.command.firebaseToken.create;

import com.kynsof.identity.domain.dto.FirebaseTokenDto;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IFirebaseTokenService;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateFirebaseTokenCommandHandler implements ICommandHandler<CreateFirebaseTokenCommand> {

    private final IUserSystemService service;
    private final IFirebaseTokenService firebaseTokenService;

    public CreateFirebaseTokenCommandHandler(IUserSystemService service, IFirebaseTokenService firebaseTokenService) {
        this.service = service;
        this.firebaseTokenService = firebaseTokenService;
    }

    @Override
    public void handle(CreateFirebaseTokenCommand command) {
        FirebaseTokenDto firebaseTokenDto = firebaseTokenService.findByUserSystemId(command.getUserId());
        if (firebaseTokenDto == null) {
            UserSystemDto userSystemDto = service.findById(command.getUserId());
            firebaseTokenService.create(new FirebaseTokenDto(
                    command.getId(),
                    userSystemDto,
                    command.getToken()
            ));

        } else {
            firebaseTokenDto.setToken(command.getToken());
            firebaseTokenService.update(firebaseTokenDto);
        }

    }
}
