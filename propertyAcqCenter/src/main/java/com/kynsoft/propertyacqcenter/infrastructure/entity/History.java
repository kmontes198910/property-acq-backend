package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.HistoryDto;
import com.kynsoft.propertyacqcenter.domain.enums.ListingType;
import com.kynsoft.propertyacqcenter.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class History implements Serializable {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType event;

    private Double price;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    private LocalDateTime date;

    private int daysOnMarket;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    public History(HistoryDto dto){
        this.id = dto.getId();
        this.event = dto.getEvent();
        this.price = dto.getPrice();
        this.listingType = dto.getListingType();
        this.date = dto.getListingDate();
        this.daysOnMarket = dto.getDaysOnMarket();
    }

    public HistoryDto toAggregate() {
        return HistoryDto.builder()
                .id(this.id)
                .event(this.event)
                .price(this.price)
                .listingType(this.listingType)
                .listingDate(this.date)
                .daysOnMarket(this.daysOnMarket)
                .build();
    }
}
