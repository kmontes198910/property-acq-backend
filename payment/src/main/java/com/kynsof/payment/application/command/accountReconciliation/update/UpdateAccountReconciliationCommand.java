package com.kynsof.payment.application.command.accountReconciliation.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAccountReconciliationCommand implements ICommand {

    private UUID id;
    private String code;
    private String description;
    private double cost;

    public UpdateAccountReconciliationCommand(UUID id, String code, String description, double cost) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.cost = cost;
    }

    public static UpdateAccountReconciliationCommand fromRequest(UpdateAccountReconciliationRequest request, UUID id) {
        return new UpdateAccountReconciliationCommand(
                id,
                request.getCode(), 
                request.getDescription(), 
                request.getCost()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAccountReconciliationMessage(id);
    }
}
