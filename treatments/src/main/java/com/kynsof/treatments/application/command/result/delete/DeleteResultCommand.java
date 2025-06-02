package com.kynsof.treatments.application.command.result.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteResultCommand implements ICommand {

    private final UUID id;

    public DeleteResultCommand(UUID id) {
        this.id = id;
    }

    public static DeleteResultCommand fromRequest(DeleteResultRequest request) {
        return new DeleteResultCommand(request.getId());
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteResultMessage(id);
    }
}