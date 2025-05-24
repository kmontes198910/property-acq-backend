package com.kynsoft.invoiceservice.application.command.tax.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DeleteTaxCommand implements ICommand {
    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return null;
    }
}
