package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DashboardComparablesResponse implements IResponse {

    private String formattedAddress;
    private String propertyType;
    private String lastSeenDate;
    private int squareFootage;
    private int lotSize;
    private int yearBuilt;
    private double price;
    private Double latitude;
    private Double longitude;
}
