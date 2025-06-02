package com.kynsoft.invoiceservice.application.query.tax.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Consulta para obtener un impuesto por ID
 */
@Getter
@Setter
@AllArgsConstructor
public class GetTaxByIdQuery implements IQuery {
    private UUID id;
}
