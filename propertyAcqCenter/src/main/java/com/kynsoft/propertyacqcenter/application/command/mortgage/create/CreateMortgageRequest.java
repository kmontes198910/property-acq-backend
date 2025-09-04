package com.kynsoft.propertyacqcenter.application.command.mortgage.create;

import com.kynsoft.propertyacqcenter.domain.enums.LoanType;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageExtraPaymentFrequency;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageLifetimeRateCap;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMortgageRequest {

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

    private String property;
    private MortgageType mortgageType;
    private MortgageFrequencyInterestCompounded compoundFrequency;// Frecuencia de capitalización
    private Boolean balloonPayment;
    private Boolean adjustableRateDetails;

    private String adjustableRateType;
    private String hybridArmType;
    private Double fixedRateTerm;
    private Double rateChangeInterval;
    private Double expectedRateChange;
    private Double limitRate;
    private Double limitIncrease;

    private Double howManyPayments;

    private Boolean accelerationWeeklyPayments; // Pagos semanales acelerados
    private Boolean accelerationBiWeeklyPayments;   // Pagos quincenales acelerados
    private Boolean accelerationExtraPayments;
    private MortgageLifetimeRateCap lifetimeRateCap;//

    private MortgageExtraPaymentFrequency extraPaymentFrequency; // Frecuencia de pagos extra
    private Boolean hasLoan;
    private LoanType loanType;
}
