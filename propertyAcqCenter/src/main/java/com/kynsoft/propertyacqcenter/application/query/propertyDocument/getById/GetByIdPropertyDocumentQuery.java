package com.kynsoft.propertyacqcenter.application.query.propertyDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdPropertyDocumentQuery implements IQuery {
    private UUID id;
}
