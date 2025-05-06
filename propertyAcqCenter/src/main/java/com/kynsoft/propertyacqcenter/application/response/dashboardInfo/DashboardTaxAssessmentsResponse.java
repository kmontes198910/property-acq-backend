package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DashboardTaxAssessmentsResponse implements IResponse {

    private int year;
    private int value;
    private Integer land;
    private Integer improvements;
}
