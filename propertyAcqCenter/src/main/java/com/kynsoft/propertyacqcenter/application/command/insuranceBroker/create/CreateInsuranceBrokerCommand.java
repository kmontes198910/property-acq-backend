package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateInsuranceBrokerCommand implements ICommand {

    private UUID id;
    private LocalDate closingDate;
    private UUID buyer;//Legal Entity
    private String property;
    private String lenderInfo;
    private String lenderInsurance;

    public CreateInsuranceBrokerCommand(LocalDate closingDate, UUID buyer, String property, String lenderInfo, String lenderInsurance) {
        this.id = UUID.randomUUID();
        this.closingDate = closingDate;
        this.buyer = buyer;
        this.property = property;
        this.lenderInfo = lenderInfo;
        this.lenderInsurance = lenderInsurance;
    }

    public static CreateInsuranceBrokerCommand fromRequest(CreateInsuranceBrokerRequest request) {
        return new CreateInsuranceBrokerCommand(
                request.getClosingDate(),
                request.getBuyer(),
                request.getProperty(),
                request.getLenderInfo(),
                request.getLenderInsurance()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateInsuranceBrokerMessage(id);
    }
}
