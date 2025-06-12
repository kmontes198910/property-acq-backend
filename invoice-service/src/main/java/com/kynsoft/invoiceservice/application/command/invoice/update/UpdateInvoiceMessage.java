package com.kynsoft.invoiceservice.application.command.invoice.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Clase que representa la respuesta a la solicitud de actualización de una factura
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvoiceMessage implements ICommandMessage {
    
    private UUID id;
    private boolean success;
    private String message;
}
