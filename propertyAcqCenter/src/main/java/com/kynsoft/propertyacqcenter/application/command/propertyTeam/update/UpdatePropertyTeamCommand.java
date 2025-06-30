package com.kynsoft.propertyacqcenter.application.command.propertyTeam.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePropertyTeamCommand implements ICommand {

    private UUID id;
    private String property;
    private UUID contact;
    private String perfil;

    public UpdatePropertyTeamCommand(UUID id, String property, UUID contact, String perfil) {
        this.id = id;
        this.contact = contact;
        this.perfil = perfil;
        this.property = property;
    }

    public static UpdatePropertyTeamCommand fromRequest(UpdatePropertyTeamRequest request, UUID id) {
        return new UpdatePropertyTeamCommand(
                id,
                request.getProperty(),
                request.getContact(),
                request.getPerfil()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyTeamMessage(id);
    }
}
