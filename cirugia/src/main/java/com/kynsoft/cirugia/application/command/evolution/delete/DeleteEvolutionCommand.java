package com.kynsoft.cirugia.application.command.evolution.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteEvolutionCommand implements ICommand {

    private UUID id;
    private UUID evolutionId;

    public DeleteEvolutionCommand(UUID evolutionId) {
        this.id = UUID.randomUUID();
        this.evolutionId = evolutionId;
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteEvolutionMessage(id);
    }
}