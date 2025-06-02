package com.kynsof.treatments.application.query.result.GetResultsByExternalConsultation;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetResultsByExternalConsultationIdQuery implements IQuery {
    private final UUID externalConsultationId;
}