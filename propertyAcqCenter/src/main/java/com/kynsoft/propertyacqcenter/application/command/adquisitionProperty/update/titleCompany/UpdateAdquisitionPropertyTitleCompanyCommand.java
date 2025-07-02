package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAdquisitionPropertyTitleCompanyCommand implements ICommand {

    private UUID adquisitionId;
    private LocalDate requestForEstoppelLetter;
    private CreateDocumentRequest earnestMoneyDepositConfirmation;

    public UpdateAdquisitionPropertyTitleCompanyCommand(UUID adquisitionId, LocalDate requestForEstoppelLetter, CreateDocumentRequest earnestMoneyDepositConfirmation) {
        this.adquisitionId = adquisitionId;
        this.requestForEstoppelLetter = requestForEstoppelLetter;
        this.earnestMoneyDepositConfirmation = earnestMoneyDepositConfirmation;
    }

    public static UpdateAdquisitionPropertyTitleCompanyCommand fromRequest(UpdateAdquisitionPropertyTitleCompanyRequest request, UUID adquisitionId) {
        return new UpdateAdquisitionPropertyTitleCompanyCommand(
                adquisitionId,
                request.getRequestForEstoppelLetter(),
                request.getEarnestMoneyDepositConfirmation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAdquisitionPropertyTitleCompanyMessage(adquisitionId);
    }
}
