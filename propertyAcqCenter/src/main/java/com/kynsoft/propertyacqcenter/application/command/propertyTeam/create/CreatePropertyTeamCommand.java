package com.kynsoft.propertyacqcenter.application.command.propertyTeam.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePropertyTeamCommand implements ICommand {

    private UUID id;
    private String property;
    private UUID contact;
    private String perfil;

    public CreatePropertyTeamCommand(String property, UUID contact, String perfil) {
        this.id = UUID.randomUUID();
        this.contact = contact;
        this.perfil = perfil;
        this.property = property;
    }

    public static CreatePropertyTeamCommand fromRequest(CreatePropertyTeamRequest request) {
        return new CreatePropertyTeamCommand(
                request.getProperty(),
                request.getContact(),
                request.getPerfil()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePropertyTeamMessage(id);
    }
}
