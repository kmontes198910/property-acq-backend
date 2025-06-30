package com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePropertyTeamAllCommand implements ICommand {

    private UUID id;
    private UUID buyerEntityName;
    private String property;

    public UpdatePropertyTeamAllCommand(UUID buyerEntityName, String property) {
        this.id = UUID.randomUUID();
        this.buyerEntityName = buyerEntityName;
        this.property = property;
    }

    public static UpdatePropertyTeamAllCommand fromRequest(UpdatePropertyTeamAllRequest request) {
        return new UpdatePropertyTeamAllCommand(
                request.getBuyerEntityName(),
                request.getProperty()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyTeamAllMessage(id);
    }
}
