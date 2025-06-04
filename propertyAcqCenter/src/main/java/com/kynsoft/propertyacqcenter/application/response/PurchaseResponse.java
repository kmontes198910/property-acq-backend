package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.ForeclosureStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;
    private PropertyType propertyType;
    private ForeclosureStatus foreclosureStatus;

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
    private Double windowValue;

    public PurchaseResponse(PurchaseDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.propertyType = dto.getPropertyType();
        this.foreclosureStatus = dto.getForeclosureStatus();
        this.improvements = dto.getImprovements();
        this.purchasePrice = dto.getPurchasePrice();
        this.estimatedMarketValue = dto.getEstimatedMarketValue();
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
        this.plumbing = dto.getPlumbing();
        this.roof = dto.getRoof();
        this.windowValue = dto.getWindow();
    }

}