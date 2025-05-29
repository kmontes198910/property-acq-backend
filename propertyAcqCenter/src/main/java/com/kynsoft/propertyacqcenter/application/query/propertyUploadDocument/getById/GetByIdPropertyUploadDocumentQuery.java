package com.kynsoft.propertyacqcenter.application.query.propertyUploadDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdPropertyUploadDocumentQuery implements IQuery {
    private UUID id;
}
