package com.kynsoft.medicaltest.application.query.labTestRequest.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetLabTestRequestByIdQuery implements IQuery {
    private UUID id;
}
