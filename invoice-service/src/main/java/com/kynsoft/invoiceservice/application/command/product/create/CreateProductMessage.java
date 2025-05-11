package com.kynsoft.invoiceservice.application.command.product.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateProductMessage implements ICommandMessage {
    private UUID id;
}