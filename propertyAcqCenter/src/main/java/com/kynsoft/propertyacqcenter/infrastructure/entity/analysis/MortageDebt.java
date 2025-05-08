package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.MortageDebtDto;
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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mortage_debt")
public class MortageDebt {
    @Id
    private UUID id;

    private Integer openMortages;
    private Double estimatedBalance;
    private Integer involuntaryLiens;
    private Double involuntaryAmount;

    @OneToOne(mappedBy = "mortageDebt")
    private Analysis analysis;

    public MortageDebt(MortageDebtDto dto) {
        this.id = dto.getId();
        this.openMortages = dto.getOpenMortages();
        this.estimatedBalance = dto.getEstimatedBalance();
        this.involuntaryLiens = dto.getInvoluntaryLiens();
        this.involuntaryAmount = dto.getInvoluntaryAmount();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public MortageDebtDto toAggregate() {
        return MortageDebtDto.builder()
                .id(this.id)
                .estimatedBalance(estimatedBalance)
                .involuntaryAmount(involuntaryAmount)
                .involuntaryLiens(involuntaryLiens)
                .openMortages(openMortages)
                .build();
    }
}
