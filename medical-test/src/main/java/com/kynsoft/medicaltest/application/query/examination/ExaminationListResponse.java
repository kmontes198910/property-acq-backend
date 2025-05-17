package com.kynsoft.medicaltest.application.query.examination;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationListResponse implements IResponse {
    private List<ExaminationResponse> examinations;
}
