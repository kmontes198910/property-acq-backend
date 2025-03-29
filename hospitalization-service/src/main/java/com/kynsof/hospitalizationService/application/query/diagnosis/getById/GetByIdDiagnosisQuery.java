package com.kynsof.hospitalizationService.application.query.diagnosis.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdDiagnosisQuery implements IQuery {
    private UUID id;
}
