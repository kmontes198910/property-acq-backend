package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionTitleCompanyDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionSeller {
    @Column(name = "request_for_estoppel_letter")
    private LocalDate requestForEstoppelLetter;

    @Column(name = "earnest_money_deposit_confirmation", nullable = true)
    private String earnestMoneyDepositConfirmation;

    public AdquisitionSeller(AdquisitionTitleCompanyDto dto) {
        this.requestForEstoppelLetter = dto.getRequestForEstoppelLetter();
        this.earnestMoneyDepositConfirmation = dto.getEarnestMoneyDepositConfirmation();
    }

}
