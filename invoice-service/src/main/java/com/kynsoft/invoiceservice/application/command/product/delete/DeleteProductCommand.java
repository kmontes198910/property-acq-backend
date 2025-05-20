package com.kynsoft.invoiceservice.application.command.product.delete;

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
public class DeleteProductCommand implements ICommand {
    private UUID id;
    
    @Override
    public ICommandMessage getMessage() {
        return new DeleteProductMessage(id);
    }
}
