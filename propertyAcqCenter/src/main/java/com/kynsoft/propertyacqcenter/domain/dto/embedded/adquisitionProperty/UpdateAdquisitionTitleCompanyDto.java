package com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.UUID;
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
public class UpdateAdquisitionTitleCompanyDto {
    private UUID idAdquisition;
    private LocalDate requestForEstoppelLetter;
    private String earnestMoneyDepositConfirmation;
}
