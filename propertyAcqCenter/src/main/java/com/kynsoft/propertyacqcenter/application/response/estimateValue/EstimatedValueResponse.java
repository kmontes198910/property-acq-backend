package com.kynsoft.propertyacqcenter.application.response.estimateValue;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.dto.ComparablePropertyDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.dto.EstimatedValueDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EstimatedValueResponse implements IResponse {
    private UUID id;
    private Integer price;
    private Integer priceRangeLow;
    private Integer priceRangeHigh;
    private Double latitude;
    private Double longitude;
    private List<ComparablePropertyResponse> comparables = new ArrayList<>();

    public EstimatedValueResponse(EstimatedValueDto dto) {
        this.id = dto.getId();
        this.price = dto.getPrice();
        this.priceRangeLow = dto.getPriceRangeLow();
        this.priceRangeHigh = dto.getPriceRangeHigh();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        for (ComparablePropertyDto comparable : dto.getComparables()) {
            this.comparables.add(new ComparablePropertyResponse(comparable));
        }
    }

}