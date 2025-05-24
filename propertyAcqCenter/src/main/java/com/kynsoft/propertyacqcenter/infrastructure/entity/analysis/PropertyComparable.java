package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.PropertyComparableDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comparable")
public class PropertyComparable {
    @Id
    private UUID id;

    private String formattedAddress;
    private String propertyType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSeenDate;

    private Integer squareFootage;
    private Integer lotSize;
    private Integer yearBuilt;
    private Double price;
    private Double latitude;
    private Double longitude;
    private Integer daysOnMarket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    public PropertyComparable(PropertyComparableDto dto) {
        this.id = dto.getId();
        this.formattedAddress = dto.getFormattedAddress();
        this.propertyType = dto.getPropertyType();
        this.lastSeenDate = dto.getLastSeenDate();
        this.squareFootage = dto.getSquareFootage();
        this.lotSize = dto.getLotSize();
        this.yearBuilt = dto.getYearBuilt();
        this.price = dto.getPrice();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
        this.daysOnMarket = dto.getDaysOnMarket();
    }

    public PropertyComparableDto toAggregate() {
        return PropertyComparableDto.builder()
                .id(this.id)
                .formattedAddress(formattedAddress)
                .lastSeenDate(lastSeenDate)
                .latitude(latitude)
                .longitude(longitude)
                .lotSize(lotSize)
                .price(price)
                .propertyType(propertyType)
                .squareFootage(squareFootage)
                .yearBuilt(yearBuilt)
                .daysOnMarket(daysOnMarket)
                .build();
    }

}
