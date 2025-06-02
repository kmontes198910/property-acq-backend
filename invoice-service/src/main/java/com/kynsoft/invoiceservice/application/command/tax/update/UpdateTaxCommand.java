package com.kynsoft.invoiceservice.application.command.tax.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax.TaxType;

@Getter
@Setter
public class UpdateTaxCommand implements ICommand {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal value;
    private TaxType taxType;
    private Boolean isPredetermined;
    private Boolean status;
    private UUID updatedBy;

    public static UpdateTaxCommand fromRequest(UpdateTaxRequest request, UUID id, UUID updatedBy) {
        UpdateTaxCommand command = new UpdateTaxCommand();
        command.setId(id);
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setValue(request.getValue());
        command.setTaxType(request.getTaxType());
        command.setIsPredetermined(request.getIsPredetermined());
        command.setStatus(request.getStatus());
        command.setUpdatedBy(updatedBy);
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateTaxMessage(id);
    }
}
