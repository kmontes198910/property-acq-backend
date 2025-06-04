package com.kynsoft.propertyacqcenter.application.command.sales.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteSalesMessage implements ICommandMessage {
    
    private final UUID id;

    private final String command = "DELETE_SALES";
}
