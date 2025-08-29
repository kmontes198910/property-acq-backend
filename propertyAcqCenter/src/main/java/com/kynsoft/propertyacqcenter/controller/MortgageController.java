package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.CreateMortgageCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.CreateMortgageMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.CreateMortgageRequest;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator.CreateMortgageCalculatorCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator.CreateMortgageCalculatorMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator.CreateMortgageCalculatorRequest;
import com.kynsoft.propertyacqcenter.application.command.mortgage.delete.DeleteMortgageCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.delete.DeleteMortgageMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.update.UpdateMortgageCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.update.UpdateMortgageMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.update.UpdateMortgageRequest;
import com.kynsoft.propertyacqcenter.application.query.mortgage.getById.GetByIdMortgageQuery;
import com.kynsoft.propertyacqcenter.application.query.mortgage.search.GetSearchMortgageQuery;
import com.kynsoft.propertyacqcenter.application.response.MortgageResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mortgage")
public class MortgageController {

    private final IMediator mediator;

    public MortgageController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateMortgageRequest request) {
        CreateMortgageMessage response = mediator.send(CreateMortgageCommand
                .builder()
                .id(UUID.randomUUID())
                .property(request.getProperty())
                .mortgageType(request.getMortgageType())
                .fixedRateTermYears(request.getFixedRateTermYears())
                .fixedMortgageRatePercentage(request.getFixedMortgageRatePercentage())
                .compoundFrequency(request.getCompoundFrequency())
                .balloonPayment(request.getBalloonPayment())
                .adjustableRateDetails(request.getAdjustableRateDetails())
                .fixedRateTermMonths(request.getFixedRateTermMonths())
                .adjustableRateType(request.getAdjustableRateType())
                .hybridArmType(request.getHybridArmType())
                .fixedRateTerm(request.getFixedRateTerm())
                .rateChangeInterval(request.getRateChangeInterval())
                .expectedRateChange(request.getExpectedRateChange())
                .limitRate(request.getLimitRate())
                .limitIncrease(request.getLimitIncrease())
                .howManyPayments(request.getHowManyPayments())
                .accelerationWeeklyPayments(request.getAccelerationWeeklyPayments())
                .accelerationExtraPayments(request.getAccelerationExtraPayments())
                .lifetimeRateCap(request.getLifetimeRateCap())
                .extraPaymentFrequency(request.getExtraPaymentFrequency())
                .purchasePrice(request.getPurchasePrice())
                .downPayment(request.getDownPayment())
                .loanTermYears(request.getLoanTermYears())
                .interestRate(request.getInterestRate())
                .loanStartDate(request.getLoanStartDate())
                .firstPaymentDate(request.getFirstPaymentDate())
                .extraPayments(request.getExtraPayments())
                .extraPaymentAmount(request.getExtraPaymentAmount())
                .build());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/calculator")
    public ResponseEntity<?> calcularor(@RequestBody CreateMortgageCalculatorRequest request) {
        CreateMortgageCalculatorMessage response = mediator.send(CreateMortgageCalculatorCommand
                .builder()
                .purchasePrice(request.getPurchasePrice())
                .downPayment(request.getDownPayment())
                .loanTermYears(request.getLoanTermYears())
                .interestRate(request.getInterestRate())
                .loanStartDate(request.getLoanStartDate())
                .firstPaymentDate(request.getFirstPaymentDate())
                .extraPayments(request.getExtraPayments())
                .extraPaymentAmount(request.getExtraPaymentAmount())
                .build());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateMortgageRequest request) {

        UpdateMortgageCommand command = UpdateMortgageCommand.fromRequest(request, id);
        UpdateMortgageMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteMortgageCommand command = new DeleteMortgageCommand(id);
        DeleteMortgageMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdMortgageQuery query = new GetByIdMortgageQuery(id);
        MortgageResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchMortgageQuery query = new GetSearchMortgageQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
