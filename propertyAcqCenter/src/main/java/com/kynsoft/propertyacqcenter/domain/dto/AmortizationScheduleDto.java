package com.kynsoft.propertyacqcenter.domain.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationScheduleDto {
    private Double monthlyPayment;//Mensualidad
    private Double totalInterestPaid;//Intereses a pagar
    private Double totalPrincipalPaid;//MortgageAmount
    private List<AmortizationEntryDto> entries = new ArrayList<>();
}
