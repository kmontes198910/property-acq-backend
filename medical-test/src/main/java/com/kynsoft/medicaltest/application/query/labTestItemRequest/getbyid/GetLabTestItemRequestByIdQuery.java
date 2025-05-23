package com.kynsoft.medicaltest.application.query.labTestItemRequest.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetLabTestItemRequestByIdQuery implements IQuery {
    private UUID id;
}
