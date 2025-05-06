package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class DashboardInfoResponse implements IResponse {
    private DashboardPropertyResponse propertyResponse;
    private DashboardOpportunnityResponse opportunnityResponse;
    private List<DashboardValueResponse> valueResponse;
    private DashboardMortageDebtResponse mortageDebtResponse;
    private DashboardCompsAtAGlanceResponse compsAtAGlanceResponse;
    private DashboardLastSaleResponse lastSaleResponse;
}
