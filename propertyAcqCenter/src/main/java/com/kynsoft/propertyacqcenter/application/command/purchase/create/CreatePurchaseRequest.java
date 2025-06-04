package com.kynsoft.propertyacqcenter.application.command.purchase.create;

import com.kynsoft.propertyacqcenter.domain.enums.ForeclosureStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePurchaseRequest {

    private String property;
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
    private Double window;
}
