package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PurchaseDto;
import com.kynsoft.propertyacqcenter.domain.enums.PurchaseType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "purchase")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Purchase implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    //@Enumerated(EnumType.STRING)
    //private ForeclosureStatus foreclosureStatus;
    @Column(name = "foreclosure", nullable = true)
    private Boolean foreclosure;
    private Double amountOfDefault;
    private Double accruedInterest;
    private Double otherFees;

    @Column(name = "purchase_improvements", nullable = true)
    private Double improvements;//TODO: por definir, es un autocalculable
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
    private Double windowValue;

    @Column(name = "closing_cost", nullable = true)
    private Double closingCost;

    public Purchase(PurchaseDto dto) {
        this.id = dto.getId();
        this.purchaseType = dto.getPurchaseType();
        this.foreclosure = dto.getForeclosure();
        this.amountOfDefault = dto.getAmountOfDefault();
        this.accruedInterest = dto.getAccruedInterest();
        this.otherFees = dto.getOtherFees();
        this.improvements = dto.getImprovements();
        this.purchasePrice = dto.getPurchasePrice();
        this.estimatedMarketValue = dto.getEstimatedMarketValue();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;

        this.acHeatPump = dto.getAcHeatPump();
        this.basement = dto.getBasement();
        this.ceiling = dto.getCeiling();
        this.deck = dto.getDeck();
        this.electrical = dto.getElectrical();
        this.exteriorPaint = dto.getExteriorPaint();
        this.fundation = dto.getFundation();
        this.heating = dto.getHeating();
        this.ketchen = dto.getKetchen();
        this.poolSpaRepair = dto.getPoolSpaRepair();
        this.skylight = dto.getSkylight();
        this.other = dto.getOther();
        this.alarm = dto.getAlarm();
        this.bathroom = dto.getBathroom();
        this.chimney = dto.getChimney();
        this.door = dto.getDoor();
        this.equipment = dto.getEquipment();
        this.fireplace = dto.getFireplace();
        this.garage = dto.getGarage();
        this.interiorPaint = dto.getInteriorPaint();
        this.landscaping = dto.getLandscaping();
        this.porch = dto.getPorch();
        this.walls = dto.getWalls();
        this.attic = dto.getAttic();
        this.carpet = dto.getCarpet();
        this.cladding = dto.getCladding();
        this.driveway = dto.getDriveway();
        this.exterior = dto.getExterior();
        this.flooring = dto.getFlooring();
        this.glutter = dto.getGlutter();
        this.irrigationSpri = dto.getIrrigationSpri();
        this.plumbing = dto.getPlumbing();
        this.roof = dto.getRoof();
        this.windowValue = dto.getWindow();
        this.closingCost = dto.getClosingCost();
    }

    public PurchaseDto toAggregate() {
        return PurchaseDto.builder()
                .id(this.id)
                .acHeatPump(acHeatPump)
                .basement(basement)
                .ceiling(ceiling)
                .deck(deck)
                .electrical(electrical)
                .exteriorPaint(exteriorPaint)
                .fundation(fundation)
                .heating(heating)
                .ketchen(ketchen)
                .poolSpaRepair(poolSpaRepair)
                .skylight(skylight)
                .other(other)
                .alarm(alarm)
                .bathroom(bathroom)
                .chimney(chimney)
                .door(door)
                .equipment(equipment)
                .fireplace(fireplace)
                .garage(garage)
                .interiorPaint(interiorPaint)
                .landscaping(landscaping)
                .porch(porch)
                .walls(walls)
                .attic(attic)
                .carpet(carpet)
                .cladding(cladding)
                .driveway(driveway)
                .exterior(exterior)
                .flooring(flooring)
                .glutter(glutter)
                .irrigationSpri(irrigationSpri)
                .plumbing(plumbing)
                .roof(roof)
                .window(windowValue)
                .property(property.toAggregateBasic())
                .purchaseType(purchaseType)
                .foreclosure(foreclosure)
                .amountOfDefault(amountOfDefault)
                .accruedInterest(accruedInterest)
                .otherFees(otherFees)
                .improvements(improvements)
                .purchasePrice(purchasePrice)
                .estimatedMarketValue(estimatedMarketValue)
                .closingCost(closingCost)
                .build();
    }
}
