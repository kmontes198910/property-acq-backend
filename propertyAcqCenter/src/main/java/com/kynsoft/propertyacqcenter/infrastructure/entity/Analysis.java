package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.CompsAtAGlance;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.LastSale;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.MortageDebt;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.Opportunity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.PropertyComparable;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.SaleValue;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.Statistics;
import com.kynsoft.propertyacqcenter.infrastructure.entity.analysis.TaxAssessmentAnalysis;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "analysis")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Analysis {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opportunity_id", referencedColumnName = "id")
    private Opportunity opportunity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistics_id", referencedColumnName = "id")
    private Statistics statistics;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mortage_debt_id", referencedColumnName = "id")
    private MortageDebt mortageDebt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comps_at_glance_id", referencedColumnName = "id")
    private CompsAtAGlance compsAtAGlance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "last_sale_id", referencedColumnName = "id")
    private LastSale lastSale;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleValue> saleValue = new HashSet<>();

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaxAssessmentAnalysis> taxAssessments = new HashSet<>();;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PropertyComparable> comparables = new HashSet<>();;

    @Column(name = "created_by")
    private UUID createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Analysis(AnalysisDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;

        // Convertir DTOs a entidades
        if (dto.getOpportunity() != null) {
            this.opportunity = new Opportunity(dto.getOpportunity());
            this.opportunity.setAnalysis(this);
        }
        if (dto.getStatistics() != null) {
            this.statistics = new Statistics(dto.getStatistics());
            this.statistics.setAnalysis(this);
        }

        if (dto.getMortageDebt() != null) {
            this.mortageDebt = new MortageDebt(dto.getMortageDebt());
            this.mortageDebt.setAnalysis(this);
        }

        if (dto.getCompsAtAGlance() != null) {
            this.compsAtAGlance = new CompsAtAGlance(dto.getCompsAtAGlance());
            this.compsAtAGlance.setAnalysis(this);
        }

        if (dto.getLastSale() != null) {
            this.lastSale = new LastSale(dto.getLastSale());
            this.lastSale.setAnalysis(this);
        }

        // Procesar listas
        if (dto.getSaleValue() != null) {
            dto.getSaleValue().forEach(svDto -> {
                SaleValue sv = new SaleValue(svDto);
                sv.setAnalysis(this);
                this.saleValue.add(sv);
            });
        }

        if (dto.getTaxAssessments() != null) {
            dto.getTaxAssessments().forEach(taDto -> {
                TaxAssessmentAnalysis ta = new TaxAssessmentAnalysis(taDto);
                ta.setAnalysis(this);
                this.taxAssessments.add(ta);
            });
        }

        if (dto.getComparables() != null) {
            dto.getComparables().forEach(pcDto -> {
                PropertyComparable pc = new PropertyComparable(pcDto);
                pc.setAnalysis(this);
                this.comparables.add(pc);
            });
        }
        this.createdBy = dto.getCreatedBy();
    }

    public AnalysisDto toAggregate() {
        return AnalysisDto.builder()
                .id(id)
                .property(property != null ? property.toAggregate() : null)
                .comparables(comparables != null ? comparables.stream().map(PropertyComparable::toAggregate).collect(Collectors.toList()) : null)
                .compsAtAGlance(compsAtAGlance != null ? compsAtAGlance.toAggregate() : null)
                .lastSale(lastSale != null ? lastSale.toAggregate() : null)
                .mortageDebt(mortageDebt != null ? mortageDebt.toAggregate() : null)
                .opportunity(opportunity != null ? opportunity.toAggregate() : null)
                .saleValue(saleValue != null ? saleValue.stream().map(SaleValue::toAggregate).collect(Collectors.toList()) : null)
                .taxAssessments(taxAssessments != null ? taxAssessments.stream().map(TaxAssessmentAnalysis::toAggregate).collect(Collectors.toList()) : null)
                .statistics(statistics != null ? statistics.toAggregate() : null)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .build();
    }

}
