package com.kynsoft.propertyacqcenter.application.query.legalEntity.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdLegalEntityQuery implements IQuery {
    private UUID id;
}
