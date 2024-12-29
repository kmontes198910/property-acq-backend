package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.treatments.application.command.billing.create.CreateBillingRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateAllBillingCommand implements ICommand {
    private boolean result;
    private final UUID patientId;
    private final   UUID businessId;
    private final boolean isProforma;
    private final List<CreateBillingPartialRequest> billingPartialRequests;

    public CreateAllBillingCommand(UUID patientId, UUID businessId, boolean isProforma, List<CreateBillingPartialRequest> billingPartialRequests) {
        this.patientId = patientId;
        this.businessId = businessId;
        this.isProforma = isProforma;
        this.billingPartialRequests = billingPartialRequests;
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateAllBillingMessage(result);
    }
}
