package com.kynsoft.propertyacqcenter.application.query.dashboardRequest;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardCompsAtAGlanceResponse;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardInfoResponse;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardLastSaleResponse;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardMortageDebtResponse;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardOpportunnityResponse;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardPropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.RentCastEstimateValueServiceImpl;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.RentCastPropertyServiceImpl;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.rentEstimate.RentCastRentEstimateServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GetDashboardInfoQueryHandler implements IQueryHandler<GetDashboardInfoQuery, DashboardInfoResponse>{

    private final RentCastEstimateValueServiceImpl estimateValueService;
    private final RentCastPropertyServiceImpl propertyService;
    private final RentCastRentEstimateServiceImpl rentCastRentEstimateService;

    public GetDashboardInfoQueryHandler(RentCastEstimateValueServiceImpl estimateValueService, 
                                        RentCastPropertyServiceImpl propertyService, 
                                        RentCastRentEstimateServiceImpl rentCastRentEstimateService) {
        this.estimateValueService = estimateValueService;
        this.propertyService = propertyService;
        this.rentCastRentEstimateService = rentCastRentEstimateService;
    }

    @Override
    public DashboardInfoResponse handle(GetDashboardInfoQuery query) {

        List<PropertyResponse> property = this.propertyService.getPropertyDetails(query.getAddress());
        List<DashboardValueResponse> values = new ArrayList<>();
        Map<String, PropertyResponse.History> history = property.get(0).getHistory();
        history.forEach((date, h) -> {
            values.add(DashboardValueResponse.builder()
                    .estimatedValue(String.valueOf(h.getPrice()))
                    .lastYear(h.getDate())
                    .build());
        });

        return DashboardInfoResponse
                .builder()
                .propertyResponse(DashboardPropertyResponse.builder()
                        .id(property.get(0).getId())
                        .apn(property.get(0).getAssessorID())
                        .county(property.get(0).getCounty())
                        .distressed("")
                        .hoaCoa("")
                        .lengthOfOwnership("")
                        .lotSize(property.get(0).getLotSize())
                        .occupancy(property.get(0).isOwnerOccupied())
                        .ownerStatus("")
                        .ownerType("")
                        .propertyType(PropertyType.valueOf(property.get(0).getPropertyType()))
                        .purchaseMethod("")
                        .shortSale("")
                        .status("")
                        .yearBuilt(property.get(0).getYearBuilt())
                        .addressLine1(property.get(0).getAddressLine1())
                        .addressLine2(property.get(0).getAddressLine2())
                        .city(property.get(0).getCity())
                        .state(property.get(0).getState())
                        .zipCode(property.get(0).getZipCode())
                        .unitCount(0)
                        .squareFootage(property.get(0).getSquareFootage())
                        .build()
                )
                .compsAtAGlanceResponse(DashboardCompsAtAGlanceResponse.builder().build())
                .lastSaleResponse(DashboardLastSaleResponse.builder().build())
                .mortageDebtResponse(DashboardMortageDebtResponse.builder().build())
                .opportunnityResponse(DashboardOpportunnityResponse.builder().build())
                .valueResponse(values)
                .build();
    }
}
