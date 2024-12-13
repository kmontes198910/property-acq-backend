package com.kynsof.treatments.application.query.reports.contConsultByYears;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ContConsultByYearsResponse implements IResponse {
    private List<Long> values;
}
