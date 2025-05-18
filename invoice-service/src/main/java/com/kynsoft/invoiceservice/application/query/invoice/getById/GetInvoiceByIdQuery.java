package com.kynsoft.invoiceservice.application.query.invoice.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Query para obtener una factura por su ID
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceByIdQuery implements IQuery {
    private UUID id;
}
