package com.kynsoft.propertyacqcenter.application.command.propertyTeam.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePropertyTeamMessage implements ICommandMessage {
    private final String command = "DELETE_PROPERTY_TEAM";

    private final UUID id;

}
