package com.kynsoft.invoiceservice.application.command.Issuer.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateInvoiceIssuerMessage implements ICommandMessage {
    private UUID id;
}