package com.kynsoft.propertyacqcenter.application.command.purchase.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.PurchaseType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePurchaseCommand implements ICommand {

    private UUID id;
    private String property;
    private PurchaseType purchaseType;
    //private ForeclosureStatus foreclosureStatus;
    private Boolean foreclosure;
    private Double amountOfDefault;
    private Double accruedInterest;
    private Double otherFees;

    private Double improvements;
    private Double purchasePrice;
    private Double estimatedMarketValue;

    private Double acHeatPump;
    private Double basement;
    private Double ceiling;
    private Double deck;
    private Double electrical;
    private Double exteriorPaint;
    private Double fundation;
    private Double heating;
    private Double ketchen;
    private Double poolSpaRepair;
    private Double skylight;
    private Double other;
    private Double alarm;
    private Double bathroom;
    private Double chimney;
    private Double door;
    private Double equipment;
    private Double fireplace;
    private Double garage;
    private Double interiorPaint;
    private Double landscaping;
    private Double porch;
    private Double walls;
    private Double attic;
    private Double carpet;
    private Double cladding;
    private Double driveway;
    private Double exterior;
    private Double flooring;
    private Double glutter;
    private Double irrigationSpri;
    private Double plumbing;
    private Double roof;
    private Double window;

    public UpdatePurchaseCommand(UUID id, String property, PurchaseType purchaseType, 
                                 Boolean foreclosure, Double improvements, Double purchasePrice, 
                                 Double estimatedMarketValue, Double acHeatPump, Double basement, 
                                 Double ceiling, Double deck, Double electrical, Double exteriorPaint, 
                                 Double fundation, Double heating, Double ketchen, Double poolSpaRepair, 
                                 Double skylight, Double other, Double alarm, Double bathroom, 
                                 Double chimney, Double door, Double equipment, Double fireplace, 
                                 Double garage, Double interiorPaint, Double landscaping, Double porch, 
                                 Double walls, Double attic, Double carpet, Double cladding, Double driveway, 
                                 Double exterior, Double flooring, Double glutter, Double irrigationSpri, 
                                 Double plumbing, Double roof, Double window,
                                 Double amountOfDefault, Double accruedInterest, Double otherFees) {
        this.id = id;
        this.property = property;
        this.purchaseType = purchaseType;
        this.foreclosure = foreclosure;
        this.amountOfDefault = amountOfDefault;
        this.accruedInterest = accruedInterest;
        this.otherFees = otherFees;
        this.improvements = improvements;
        this.purchasePrice = purchasePrice;
        this.estimatedMarketValue = estimatedMarketValue;
        this.acHeatPump = acHeatPump;
        this.basement = basement;
        this.ceiling = ceiling;
        this.deck = deck;
        this.electrical = electrical;
        this.exteriorPaint = exteriorPaint;
        this.fundation = fundation;
        this.heating = heating;
        this.ketchen = ketchen;
        this.poolSpaRepair = poolSpaRepair;
        this.skylight = skylight;
        this.other = other;
        this.alarm = alarm;
        this.bathroom = bathroom;
        this.chimney = chimney;
        this.door = door;
        this.equipment = equipment;
        this.fireplace = fireplace;
        this.garage = garage;
        this.interiorPaint = interiorPaint;
        this.landscaping = landscaping;
        this.porch = porch;
        this.walls = walls;
        this.attic = attic;
        this.carpet = carpet;
        this.cladding = cladding;
        this.driveway = driveway;
        this.exterior = exterior;
        this.flooring = flooring;
        this.glutter = glutter;
        this.irrigationSpri = irrigationSpri;
        this.plumbing = plumbing;
        this.roof = roof;
        this.window = window;
    }

    public static UpdatePurchaseCommand fromRequest(UpdatePurchaseRequest request, UUID id) {
        return new UpdatePurchaseCommand(
                id,
                request.getProperty(),
                request.getPurchaseType(),
                request.getForeclosure(),
                request.getImprovements(),
                request.getPurchasePrice(),
                request.getEstimatedMarketValue(),
                request.getAcHeatPump(),
                request.getBasement(),
                request.getCeiling(),
                request.getDeck(),
                request.getElectrical(),
                request.getExteriorPaint(),
                request.getFundation(),
                request.getHeating(),
                request.getKetchen(),
                request.getPoolSpaRepair(),
                request.getSkylight(),
                request.getOther(),
                request.getAlarm(),
                request.getBathroom(),
                request.getChimney(),
                request.getDoor(),
                request.getEquipment(),
                request.getFireplace(),
                request.getGarage(),
                request.getInteriorPaint(),
                request.getLandscaping(),
                request.getPorch(),
                request.getWalls(),
                request.getAttic(),
                request.getCarpet(),
                request.getCladding(),
                request.getDriveway(),
                request.getExterior(),
                request.getFlooring(),
                request.getGlutter(),
                request.getIrrigationSpri(),
                request.getPlumbing(),
                request.getRoof(),
                request.getWindow(),
                request.getAmountOfDefault(),
                request.getAccruedInterest(),
                request.getOtherFees()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePurchaseMessage(id);
    }
}
