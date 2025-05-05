package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DashboardValueResponse implements IResponse {
    private String estimatedValue;
    private String lastYear;
}
