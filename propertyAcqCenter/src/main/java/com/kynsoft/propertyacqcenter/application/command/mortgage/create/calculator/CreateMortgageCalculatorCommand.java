package com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.dto.AmortizationScheduleDto;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMortgageCalculatorCommand implements ICommand {

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

    private AmortizationScheduleDto amortizations;

    @Override
    public ICommandMessage getMessage() {
        return new CreateMortgageCalculatorMessage(amortizations);
    }
}
