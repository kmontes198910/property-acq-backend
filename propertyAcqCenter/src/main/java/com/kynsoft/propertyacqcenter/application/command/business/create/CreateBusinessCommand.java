package com.kynsoft.propertyacqcenter.application.command.business.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBusinessCommand implements ICommand {

    private UUID id;
    private String name;

    public CreateBusinessCommand(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public static CreateBusinessCommand fromRequest(CreateBusinessRequest request) {
        return new CreateBusinessCommand(
                request.getName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBusinessMessage(id);
    }
}
