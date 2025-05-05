package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DashboardCompsAtAGlanceResponse implements IResponse {
    private String avgSalePrice;
    private String dayOnMarket;
}
