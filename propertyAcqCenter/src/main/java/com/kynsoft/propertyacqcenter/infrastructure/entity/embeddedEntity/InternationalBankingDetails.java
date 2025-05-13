package com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.InternationalBankingDetailsDto;
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

    public InternationalBankingDetails(InternationalBankingDetailsDto dto) {
        this.swiftCode = dto.getSwiftCode();
        this.iban = dto.getIban();
    }

}
