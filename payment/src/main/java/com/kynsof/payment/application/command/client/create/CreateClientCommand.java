package com.kynsof.payment.application.command.client.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateClientCommand implements ICommand {

    private UUID id;
    private String identification;
    private String name;
    private String lastName;

    public CreateClientCommand(String identification, String name, String lastName) {

        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
    }

    public static CreateClientCommand fromRequest(CreateClientRequest request) {
        return new CreateClientCommand(
                request.getIdentification(), 
                request.getName(), 
                request.getLastName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateClientMessage(id);
    }
}
