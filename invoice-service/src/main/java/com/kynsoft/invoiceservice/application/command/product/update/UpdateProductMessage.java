package com.kynsoft.invoiceservice.application.command.product.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateProductMessage implements ICommandMessage {
    private UUID id;
}