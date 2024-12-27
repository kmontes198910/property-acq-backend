package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.treatments.application.command.billing.create.CreateBillingRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateAllBillingCommand implements ICommand {

    private List<CreateBillingRequest> payload;

    public CreateAllBillingCommand(List<CreateBillingRequest> payload) {
        this.payload = payload;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAllBillingMessage();
    }
}
