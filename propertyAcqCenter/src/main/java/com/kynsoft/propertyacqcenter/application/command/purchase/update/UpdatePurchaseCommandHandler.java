package com.kynsoft.propertyacqcenter.application.command.purchase.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PurchaseDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class UpdatePurchaseCommandHandler implements ICommandHandler<UpdatePurchaseCommand> {

    private final IPurchaseService purchaseService;
    private final IPropertyService propertyService;

    public UpdatePurchaseCommandHandler(IPurchaseService purchaseService, IPropertyService propertyService) {
        this.purchaseService = purchaseService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdatePurchaseCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.purchaseService.update(PurchaseDto.builder()
                .id(command.getId())
                .property(property)
                .propertyType(command.getPropertyType())
                .estimatedMarketValue(command.getEstimatedMarketValue())
                .foreclosureStatus(command.getForeclosureStatus())
                .improvements(command.getImprovements())
                .purchasePrice(command.getPurchasePrice())
                
                .acHeatPump(command.getAcHeatPump())
                .basement(command.getBasement())
                .ceiling(command.getCeiling())
                .deck(command.getDeck())
                .electrical(command.getElectrical())
                .exteriorPaint(command.getExteriorPaint())
                .fundation(command.getFundation())
                .heating(command.getHeating())
                .ketchen(command.getKetchen())
                .poolSpaRepair(command.getPoolSpaRepair())
                .skylight(command.getSkylight())
                .other(command.getOther())
                .alarm(command.getAlarm())
                .bathroom(command.getBathroom())
                .chimney(command.getChimney())
                .door(command.getDoor())
                .equipment(command.getEquipment())
                .fireplace(command.getFireplace())
                .garage(command.getGarage())
                .interiorPaint(command.getInteriorPaint())
                .landscaping(command.getLandscaping())
                .porch(command.getPorch())
                .walls(command.getWalls())
                .attic(command.getAttic())
                .carpet(command.getCarpet())
                .cladding(command.getCladding())
                .driveway(command.getDriveway())
                .exterior(command.getExterior())
                .flooring(command.getFlooring())
                .glutter(command.getGlutter())
                .irrigationSpri(command.getIrrigationSpri())
                .plumbing(command.getPlumbing())
                .roof(command.getRoof())
                .window(command.getWindow())

                .build()
        );
    }

}
