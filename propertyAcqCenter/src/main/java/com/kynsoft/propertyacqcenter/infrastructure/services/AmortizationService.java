package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsoft.propertyacqcenter.domain.dto.AmortizationEntryDto;
import com.kynsoft.propertyacqcenter.domain.dto.AmortizationScheduleDto;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class AmortizationService {

    public AmortizationScheduleDto generateAmortizationSchedule(MortgageDto request) {
        Double principal = request.getPurchasePrice() - request.getDownPayment();
        Double monthlyRate = request.getInterestRate() / 100 / 12;
        int totalPayments = request.getLoanTermYears() * 12;
        Double monthlyPayment = calculateMonthlyPayment(principal, monthlyRate, totalPayments);

        AmortizationScheduleDto schedule = new AmortizationScheduleDto();
        schedule.setMonthlyPayment(monthlyPayment);

        double remainingBalance = principal;
        double totalInterest = 0.0;
        LocalDate paymentDate = request.getFirstPaymentDate() != null
                ? request.getFirstPaymentDate()
                : LocalDate.now().plusMonths(1);

        for (int paymentNumber = 1; paymentNumber <= totalPayments; paymentNumber++) {
            double interestPaid = remainingBalance * monthlyRate;
            double principalPaid = monthlyPayment - interestPaid;

            // Ajuste para el último pago
            if (paymentNumber == totalPayments) {
                principalPaid = remainingBalance;
                monthlyPayment = principalPaid + interestPaid;
            }

            remainingBalance -= principalPaid;
            totalInterest += interestPaid;

            AmortizationEntryDto entry = new AmortizationEntryDto(
                    paymentNumber,
                    paymentDate,
                    Math.round(monthlyPayment * 100.0) / 100.0,
                    Math.round(principalPaid * 100.0) / 100.0,
                    Math.round(interestPaid * 100.0) / 100.0,
                    Math.round(Math.max(0, remainingBalance) * 100.0) / 100.0
            );

            schedule.getEntries().add(entry);
            paymentDate = paymentDate.plusMonths(1);
        }

        schedule.setTotalInterestPaid(Math.round(totalInterest * 100.0) / 100.0);
        schedule.setTotalPrincipalPaid(principal);

        return schedule;
    }

    private Double calculateMonthlyPayment(Double principal, Double monthlyRate, int totalPayments) {
        return principal * (monthlyRate * Math.pow(1 + monthlyRate, totalPayments))
                / (Math.pow(1 + monthlyRate, totalPayments) - 1);
    }
}
