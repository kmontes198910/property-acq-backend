package com.kynsoft.medicaltest.application.query.examinationorder;

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
public class ExaminationOrderListResponse implements IResponse {
    private List<ExaminationOrderResponse> orders;
}
