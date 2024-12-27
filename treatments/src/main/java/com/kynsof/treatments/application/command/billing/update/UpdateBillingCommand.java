package com.kynsof.treatments.application.command.billing.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBillingCommand implements ICommand {

    private UUID id;
    private String name;
    private final String presentation;

    public UpdateBillingCommand(String name, UUID id, String presentation) {
        this.id = id;
        this.name = name;
        this.presentation = presentation;
    }

    public static UpdateBillingCommand fromRequest(UpdateBillingRequest request, UUID id) {
        return new UpdateBillingCommand(
                request.getName(),
                id,
                request.getPresentation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBillingMessage(id);
    }
}
