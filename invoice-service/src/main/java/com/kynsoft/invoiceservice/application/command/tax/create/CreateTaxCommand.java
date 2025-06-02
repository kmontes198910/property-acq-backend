package com.kynsoft.invoiceservice.application.command.tax.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax.TaxType;

@Getter
@Setter
public class CreateTaxCommand implements ICommand {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private BigDecimal value;
    private TaxType taxType;
    private Boolean isPredetermined;
    private Boolean status;
    private UUID createdBy;

    public CreateTaxCommand() {
        this.id = UUID.randomUUID();
        this.status = true;
    }

    public static CreateTaxCommand fromRequest(CreateTaxRequest request, UUID createdBy) {
        CreateTaxCommand command = new CreateTaxCommand();
        command.setCode(request.getCode());
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setValue(request.getValue());
        command.setTaxType(request.getTaxType());
        command.setIsPredetermined(request.getIsPredetermined());
        command.setCreatedBy(createdBy);
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateTaxMessage(id);
    }
}
