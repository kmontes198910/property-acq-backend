package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.update;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateOwnerShipLegalEntityRequest {

    private String name;
    private Double ownershipPercentage;
    private UUID legalEntity;
}
