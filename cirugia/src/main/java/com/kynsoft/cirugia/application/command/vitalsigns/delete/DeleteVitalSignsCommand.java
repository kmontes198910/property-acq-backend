package com.kynsoft.cirugia.application.command.vitalsigns.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteVitalSignsCommand implements ICommand {
    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteVitalSignsMessage(id);
    }
}