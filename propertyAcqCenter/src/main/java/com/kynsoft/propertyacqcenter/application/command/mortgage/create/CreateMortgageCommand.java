package com.kynsoft.propertyacqcenter.application.command.mortgage.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.LoanType;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageExtraPaymentFrequency;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageLifetimeRateCap;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMortgageCommand implements ICommand {

    private UUID id;
    private String property;
    private MortgageType mortgageType;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private MortgageFrequencyInterestCompounded compoundFrequency;
    private Boolean balloonPayment;
    private Boolean adjustableRateDetails;
    private Integer fixedRateTermMonths;

    private String adjustableRateType;
    private String hybridArmType;
    private Double fixedRateTerm;
    private Double rateChangeInterval;
    private Double expectedRateChange;
    private Double limitRate;
    private Double limitIncrease;

    private Double howManyPayments;

    private Boolean accelerationWeeklyPayments;
    private Boolean accelerationExtraPayments;
    private MortgageLifetimeRateCap lifetimeRateCap;//

    private MortgageExtraPaymentFrequency extraPaymentFrequency;//

    // ✅ ESENCIALES (mantener)
    private Double purchasePrice;        // Precio de compra ($)
    private Double downPayment;          // Pago inicial ($)
    private Boolean isPercentage;          // Define si el downPayment esta en % o no.
    private Integer loanTermYears;       // Plazo (15, 20, 30 años)
    private Double interestRate;         // Tasa de interés anual (%)
    private LocalDate loanStartDate;     // Fecha de inicio

    // ✅ OPCIONALES pero útiles
    private LocalDate firstPaymentDate;  // Fecha primer pago
    private Boolean extraPayments;       // Pagos extras
    private Double extraPaymentAmount;   // Monto pagos extras
    private Boolean hasLoan;
    private LoanType loanType;

    @Override
    public ICommandMessage getMessage() {
        return new CreateMortgageMessage(id);
    }
}
