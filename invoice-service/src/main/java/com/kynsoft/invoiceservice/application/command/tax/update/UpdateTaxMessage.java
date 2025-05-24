package com.kynsoft.invoiceservice.application.command.tax.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTaxMessage implements ICommandMessage {
    private UUID id;
}
