package com.kynsof.payment.application.command.accountReconciliation.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAccountReconciliationCommand implements ICommand {

    private UUID id;
    private String code;
    private String description;
    private double cost;
    private UUID business;

    public CreateAccountReconciliationCommand(UUID id, String code, String description, double cost, UUID business) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.cost = cost;
        this.business = business;
    }

    public static CreateAccountReconciliationCommand fromRequest(CreateAccountReconciliationRequest request) {
        return new CreateAccountReconciliationCommand(
                UUID.randomUUID(),
                request.getCode(), 
                request.getDescription(), 
                request.getCost(),
                request.getBusiness()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAccountReconciliationMessage(id);
    }
}
