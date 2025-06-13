package com.kynsoft.propertyacqcenter.application.command.allAnalysis.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ExpensesDto;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDetailsBreakdownDto;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PurchaseDto;
import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateAllAnalysisCommandHandler implements ICommandHandler<CreateAllAnalysisCommand> {

    private final IExpensesService expensesService;
    private final IIncomeService incomeService;
    private final IPurchaseService purchaseService;
    private final IMortgageService mortgageService;
    private final IPropertyService propertyService;
    private final ISalesPropertyService salesPropertyService;

    public CreateAllAnalysisCommandHandler(IExpensesService expensesService, IIncomeService incomeService, 
                                           IPurchaseService purchaseService, IMortgageService mortgageService, 
                                           IPropertyService propertyService, ISalesPropertyService salesPropertyService) {
        this.expensesService = expensesService;
        this.incomeService = incomeService;
        this.purchaseService = purchaseService;
        this.mortgageService = mortgageService;
        this.propertyService = propertyService;
        this.salesPropertyService = salesPropertyService;
    }

    @Override
    @Transactional
    public void handle(CreateAllAnalysisCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.salesPropertyService.create(SalesPropertyDto.builder()
                .id(command.getId())
                .property(property)
                .capitalGainsTaxRate(command.getSales().getCapitalGainsTaxRate())
                .stateIncomeTaxRate(command.getSales().getStateIncomeTaxRate())
                .federalIncomeTaxRate(command.getSales().getFederalIncomeTaxRate())
                .purchesePrice(command.getSales().getPurchesePrice())
                .marketValueIndreaseRate(command.getSales().getMarketValueIndreaseRate())
                .deprecationNone(command.getSales().getDeprecationNone())
                .deprecationStraightline(command.getSales().getDeprecationStraightline())
                .deprecationDoubleDecliningBalance(command.getSales().getDeprecationDoubleDecliningBalance())
                .propertysStarting(command.getSales().getPropertysStarting())
                .propertysAnnualValueIncrease(command.getSales().getPropertysAnnualValueIncrease())
                .typeOfSalesCost(command.getSales().getTypeOfSalesCost())
                .build()
        );

        this.expensesService.create(ExpensesDto.builder()
                .id(command.getId())
                .property(property)
                .totalAmountExpenses(command.getExpenses().getTotalAmountExpenses())
                .increaseRate(command.getExpenses().getIncreaseRate())
                .accounting(command.getExpenses().getAccounting())
                .electricity(command.getExpenses().getElectricity())
                .gas(command.getExpenses().getGas())
                .mortgageInsurance(command.getExpenses().getMortgageInsurance())
                .poolSpaService(command.getExpenses().getPoolSpaService())
                .sewerWater(command.getExpenses().getSewerWater())
                .trash(command.getExpenses().getTrash())
                .advertising(command.getExpenses().getAdvertising())
                .fireInsurance(command.getExpenses().getFireInsurance())
                .janitorialService(command.getExpenses().getJanitorialService())
                .liabilityInsurance(command.getExpenses().getLiabilityInsurance())
                .otherUtilities(command.getExpenses().getOtherUtilities())
                .propertyTaxes(command.getExpenses().getPropertyTaxes())
                .supplies(command.getExpenses().getSupplies())
                .workmans(command.getExpenses().getWorkmans())
                .associationFees(command.getExpenses().getAssociationFees())
                .floodInsurance(command.getExpenses().getFloodInsurance())
                .landscaping(command.getExpenses().getLandscaping())
                .licenses(command.getExpenses().getLicenses())
                .payroll(command.getExpenses().getPayroll())
                .repairMaintenance(command.getExpenses().getRepairMaintenance())
                .telephone(command.getExpenses().getTelephone())
                .miscellaneous(command.getExpenses().getMiscellaneous())
                .legal(command.getExpenses().getLegal())
                .increaseType(command.getExpenses().getIncreaseType())
                .build());

        this.incomeService.create(IncomeDto.builder()
                .id(command.getId())
                .property(property)
                .grossMonthlyIncome(command.getIncome().getGrossMonthlyIncome())
                .totalNetMonthlyIncome(command.getIncome().getTotalNetMonthlyIncome())
                .increaseRate(command.getIncome().getIncreaseRate())
                .unitType(command.getIncome().getUnitType())
                .quantity(command.getIncome().getQuantity())
                .rentMo(command.getIncome().getRentMo())
                .sqft(command.getIncome().getSqft())
                .sqftValue(command.getIncome().getSqftValue())
                .occupancy(command.getIncome().getOccupancy())
                .annualIncrease(command.getIncome().getAnnualIncrease())
                .depositForfeitures(command.getIncome().getDepositForfeitures())
                .sectino8Income(command.getIncome().getSectino8Income())
                .incomefromInterest(command.getIncome().getIncomefromInterest())
                .vendingMachines(command.getIncome().getVendingMachines())
                .lateCharges(command.getIncome().getLateCharges())
                .laundryRoom(command.getIncome().getLaundryRoom())
                .other(command.getIncome().getOther())
                .propertyManagementRate(command.getIncome().getPropertyManagementRate())
                .leasingCommissionRate(command.getIncome().getLeasingCommissionRate())
                .leasingCommision(command.getIncome().getLeasingCommision())
                .porcentageIncreaseType(command.getIncome().getPorcentageIncreaseType())
                .fixedDollarAmount(command.getIncome().getFixedDollarAmount())
                .increaseType(command.getIncome().getIncreaseType())
                .detailsBreakdown(this.detailsBreakdownValues(command))
                .build()
        );

        this.mortgageService.create(MortgageDto.builder()
                .id(command.getId())
                .property(property)
                .mortgageType(command.getMortgage().getMortgageType())
                .mortgageAmount(command.getMortgage().getMortgageAmount())
                .downPayment(command.getMortgage().getDownPayment())
                .fixedRateTermYears(command.getMortgage().getFixedRateTermYears())
                .fixedMortgageRatePercentage(command.getMortgage().getFixedMortgageRatePercentage())
                .firstPaymentDate(command.getMortgage().getFirstPaymentDate())
                .compoundFrequency(command.getMortgage().getCompoundFrequency())
                .balloonPayment(command.getMortgage().getBalloonPayment())
                .adjustableRateDetails(command.getMortgage().getAdjustableRateDetails())
                .fixedRateTermMonths(command.getMortgage().getFixedRateTermMonths())
                
                .adjustableRateType(command.getMortgage().getAdjustableRateType())
                .hybridArmType(command.getMortgage().getHybridArmType())
                .fixedRateTerm(command.getMortgage().getFixedRateTerm())
                .rateChangeInterval(command.getMortgage().getRateChangeInterval())
                .expectedRateChange(command.getMortgage().getExpectedRateChange())
                .limitRate(command.getMortgage().getLimitRate())
                .limitIncrease(command.getMortgage().getLimitIncrease())
                .howManyPayments(command.getMortgage().getHowManyPayments())
                .accelerationWeeklyPayments(command.getMortgage().getAccelerationWeeklyPayments())
                .accelerationExtraPayments(command.getMortgage().getAccelerationExtraPayments())
                .build()
        );

        this.purchaseService.create(PurchaseDto.builder()
                .id(command.getId())
                .property(property)
                .purchaseType(command.getPurchase().getPurchaseType())
                .estimatedMarketValue(command.getPurchase().getEstimatedMarketValue())
                .foreclosure(command.getPurchase().getForeclosure())
                .amountOfDefault(command.getPurchase().getAmountOfDefault())
                .accruedInterest(command.getPurchase().getAccruedInterest())
                .otherFees(command.getPurchase().getOtherFees())
                .improvements(command.getPurchase().getImprovements())
                .purchasePrice(command.getPurchase().getPurchasePrice())

                .acHeatPump(command.getPurchase().getAcHeatPump())
                .basement(command.getPurchase().getBasement())
                .ceiling(command.getPurchase().getCeiling())
                .deck(command.getPurchase().getDeck())
                .electrical(command.getPurchase().getElectrical())
                .exteriorPaint(command.getPurchase().getExteriorPaint())
                .fundation(command.getPurchase().getFundation())
                .heating(command.getPurchase().getHeating())
                .ketchen(command.getPurchase().getKetchen())
                .poolSpaRepair(command.getPurchase().getPoolSpaRepair())
                .skylight(command.getPurchase().getSkylight())
                .other(command.getPurchase().getOther())
                .alarm(command.getPurchase().getAlarm())
                .bathroom(command.getPurchase().getBathroom())
                .chimney(command.getPurchase().getChimney())
                .door(command.getPurchase().getDoor())
                .equipment(command.getPurchase().getEquipment())
                .fireplace(command.getPurchase().getFireplace())
                .garage(command.getPurchase().getGarage())
                .interiorPaint(command.getPurchase().getInteriorPaint())
                .landscaping(command.getPurchase().getLandscaping())
                .porch(command.getPurchase().getPorch())
                .walls(command.getPurchase().getWalls())
                .attic(command.getPurchase().getAttic())
                .carpet(command.getPurchase().getCarpet())
                .cladding(command.getPurchase().getCladding())
                .driveway(command.getPurchase().getDriveway())
                .exterior(command.getPurchase().getExterior())
                .flooring(command.getPurchase().getFlooring())
                .glutter(command.getPurchase().getGlutter())
                .irrigationSpri(command.getPurchase().getIrrigationSpri())
                .plumbing(command.getPurchase().getPlumbing())
                .roof(command.getPurchase().getRoof())
                .window(command.getPurchase().getWindow())
                .build()
        );

    }

    private List<IncomeDetailsBreakdownDto> detailsBreakdownValues(CreateAllAnalysisCommand command){
        List<IncomeDetailsBreakdownDto> values = new ArrayList<>();
        if (command.getIncome().getDetailsBreakdown() != null) {
            command.getIncome().getDetailsBreakdown().forEach(x -> {
                values.add(IncomeDetailsBreakdownDto.builder()
                        .id(UUID.randomUUID())
                        .unitType(x.getUnitType())
                        .quantity(x.getQuantity())
                        .rentMo(x.getRentMo())
                        .sqft(x.getSqft())
                        .sqftValue(x.getSqftValue())
                        .occupancy(x.getOccupancy())
                        .annualIncrease(x.getAnnualIncrease())
                        .build()
                );
            });
        }
        return values;
    }

}
