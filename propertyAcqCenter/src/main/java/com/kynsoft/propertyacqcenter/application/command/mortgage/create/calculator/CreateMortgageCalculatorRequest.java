package com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMortgageCalculatorRequest {

    // ✅ ESENCIALES (mantener)
    private Double purchasePrice;        // Precio de compra ($)
    private Double downPayment;          // Pago inicial ($)
    private Integer loanTermYears;       // Plazo (15, 20, 30 años)
    private Double interestRate;         // Tasa de interés anual (%)
    private LocalDate loanStartDate;     // Fecha de inicio

    // ✅ OPCIONALES pero útiles
    private LocalDate firstPaymentDate;  // Fecha primer pago
    private Boolean extraPayments;       // Pagos extras
    private Double extraPaymentAmount;   // Monto pagos extras
}
