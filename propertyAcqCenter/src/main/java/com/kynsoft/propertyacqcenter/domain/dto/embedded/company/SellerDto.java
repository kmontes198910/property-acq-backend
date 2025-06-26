package com.kynsoft.propertyacqcenter.domain.dto.embedded.company;

import jakarta.persistence.Embeddable;
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
public class SellerDto {
    private UUID id;
    private String socialSecurity;
    private String folioParcelNumber;
    private Boolean declareIfForeing;
    private String legalDescription;
    private String lenderName;
    private String loanNumber;
}
