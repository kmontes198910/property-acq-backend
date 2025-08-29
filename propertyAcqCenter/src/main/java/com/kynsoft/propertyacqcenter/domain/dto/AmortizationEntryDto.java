package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmortizationEntryDto {
    private Integer paymentNumber;
    private LocalDate paymentDate;
    private Double paymentAmount;
    private Double principalPaid;
    private Double interestPaid;
    private Double remainingBalance;
}
