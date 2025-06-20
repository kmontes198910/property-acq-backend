package com.kynsoft.propertyacqcenter.application.query.generalDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class FindByIdGeneralDocumentQuery implements IQuery {

    private final UUID id;
}
