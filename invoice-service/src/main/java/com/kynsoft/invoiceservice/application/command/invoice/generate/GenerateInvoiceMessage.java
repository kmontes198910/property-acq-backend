package com.kynsoft.invoiceservice.application.command.invoice.generate;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateInvoiceMessage implements ICommandMessage {
    private UUID id;
    private String AccessKey;
}