package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.OpportunityDto;
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

@Entity
@Table(name = "opportunity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Opportunity {

    @Id
    private UUID id;

    private String linkedProperties;
    private Double estWholesalePrice;
    private Double monthlyRent;
    private Double grossYield;

    @OneToOne(mappedBy = "opportunity")
    private Analysis analysis;

    public Opportunity(OpportunityDto dto) {
        this.id = dto.getId();
        this.linkedProperties = dto.getLinkedProperties();
        this.estWholesalePrice = dto.getEstWholesalePrice();
        this.monthlyRent = dto.getMonthlyRent();
        this.grossYield = dto.getGrossYield();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public OpportunityDto toAggregate() {
        return OpportunityDto.builder()
                .id(this.id)
                .estWholesalePrice(estWholesalePrice)
                .grossYield(grossYield)
                .linkedProperties(linkedProperties)
                .monthlyRent(monthlyRent)
                .build();
    }

}
