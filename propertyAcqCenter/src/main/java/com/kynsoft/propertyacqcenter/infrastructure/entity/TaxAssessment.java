package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.TaxAssessmentsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tax_assessments")
public class TaxAssessment {

    @Id
    private UUID id;

    private int year;
    private int value;
    private Integer land;
    private Integer improvements;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    public TaxAssessment(TaxAssessmentsDto taxAssessmentsDto) {
        this.id = taxAssessmentsDto.getId();
        this.year = taxAssessmentsDto.getYear();
        this.value = taxAssessmentsDto.getValue();
        this.land = taxAssessmentsDto.getLand();
        this.improvements = taxAssessmentsDto.getImprovements();
    }

    public TaxAssessmentsDto toAggregate() {
        return TaxAssessmentsDto.builder()
                .id(this.id)
                .year(this.year)
                .value(this.value)
                .land(this.land)
                .improvements(this.improvements)
                .build();
    }
}
