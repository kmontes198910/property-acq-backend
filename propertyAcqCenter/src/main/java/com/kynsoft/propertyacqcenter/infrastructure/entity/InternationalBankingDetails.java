package com.kynsoft.propertyacqcenter.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class InternationalBankingDetails {
    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "iban")
    private String iban;

    @Column(name = "account_currency")
    private String currency;
}
