package com.kynsof.hospitalizationService.application.query.hospitalDischargeSummary.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdHospitalDischargeSummaryQuery implements IQuery {
    private UUID id;
}
