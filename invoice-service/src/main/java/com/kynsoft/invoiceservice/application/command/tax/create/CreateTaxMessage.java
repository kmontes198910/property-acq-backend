package com.kynsoft.invoiceservice.application.command.tax.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateTaxMessage implements ICommandMessage {
    private UUID id;
}
