package com.kynsof.payment.application.command.client.create;

import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IClientService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateClientCommandHandler  implements ICommandHandler<CreateClientCommand> {

    private final IClientService serviceImpl;

    public CreateClientCommandHandler(IClientService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(CreateClientCommand command) {
       UUID id = serviceImpl.create(new ClientDto(
                UUID.randomUUID(),
                command.getIdentification(),
                command.getName(),
                command.getLastName(),
               Status.ACTIVE,
               command.getEmail(),
               command.getPhone()
        ));
       command.setId(id);
    }
}
