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
public class ClosingDto {
    private boolean finalEscrowInstructionsSigned;
    private boolean buyersClosingStatement;
    private boolean sellersClosingStatement;
    private boolean titleInsurancePolicy;
    private boolean closingProtectionLetter;
    private boolean notarizedDeed;
    private boolean billOfSale;
    private boolean entityAffidavitOfAuthority;
    private boolean irsForm1099S;
    private boolean finalBuyerWireInstructions;
    private boolean disbursementAuthorization;
    private boolean keysOrPossessionTransfer;
    private LocalDate closingDate;
}
