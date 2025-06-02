package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateInsuranceBrokerCommand implements ICommand {

    private UUID id;
    private LocalDate closingDate;
    private UUID buyer;//Legal Entity
    private String property;
    private String lenderInfo;
    private String lenderInsurance;

    public UpdateInsuranceBrokerCommand(UUID id, LocalDate closingDate, UUID buyer, String property, String lenderInfo, String lenderInsurance) {
        this.id = id;
        this.closingDate = closingDate;
        this.buyer = buyer;
        this.property = property;
        this.lenderInfo = lenderInfo;
        this.lenderInsurance = lenderInsurance;
    }

    public static UpdateInsuranceBrokerCommand fromRequest(UpdateInsuranceBrokerRequest request, UUID id) {
        return new UpdateInsuranceBrokerCommand(
                id,
                request.getClosingDate(),
                request.getBuyer(),
                request.getProperty(),
                request.getLenderInfo(),
                request.getLenderInsurance()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateInsuranceBrokerMessage(id);
    }
}
