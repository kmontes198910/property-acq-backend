package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.CompsAtAGlanceDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "comps_at_glance")
public class CompsAtAGlance {
    @Id
    private UUID id;

    private Double avgSalePrice;
    private Integer dayOnMarket;

    @OneToOne(mappedBy = "compsAtAGlance")
    private Analysis analysis;

    public CompsAtAGlance(CompsAtAGlanceDto dto) {
        this.id = dto.getId();
        this.avgSalePrice = dto.getAvgSalePrice();
        this.dayOnMarket = dto.getDayOnMarket();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public CompsAtAGlanceDto toAggregate() {
        return CompsAtAGlanceDto.builder()
                .id(this.id)
                .avgSalePrice(avgSalePrice)
                .dayOnMarket(dayOnMarket)
                .build();
    }

}
