package com.kynsoft.invoiceservice.application.command.customer.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCustomerCommand implements ICommand {
    private UUID id;
    
    @Override
    public ICommandMessage getMessage() {
        return new DeleteCustomerMessage(id);
    }
}