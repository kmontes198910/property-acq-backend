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
    private String id;
    private String propertyType;
    private String lastSeenDate;
    private Integer squareFootage;
    private Integer lotSize;
    private Integer yearBuilt;
    private Integer price;
    private Double latitude;
    private Double longitude;
    private Integer daysOnMarket;
    private Double distance;
    private String listedDate;
    private Integer bedrooms;
    private Double bathrooms;
}
