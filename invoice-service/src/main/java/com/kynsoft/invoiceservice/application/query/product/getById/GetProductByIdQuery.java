package com.kynsoft.invoiceservice.application.query.product.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetProductByIdQuery implements IQuery {
    private final UUID id;
}