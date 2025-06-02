package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import com.kynsoft.propertyacqcenter.domain.dto.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternationalBankingDetailsDto {
    private String swiftCode;
    private String iban;
    //private String currency;
    private CurrencyDto currency;
}
