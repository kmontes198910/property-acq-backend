package com.kynsoft.propertyacqcenter.application.command.bankAccount.create;

import java.util.UUID;
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
public class InternationalBankingDetailsRequest {
    private String swiftCode;
    private String iban;
    private UUID currency;
}
