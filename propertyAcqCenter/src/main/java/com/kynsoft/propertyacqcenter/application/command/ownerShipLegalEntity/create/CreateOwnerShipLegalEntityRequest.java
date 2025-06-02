package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.create;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateOwnerShipLegalEntityRequest {

    private String name;
    private Double ownershipPercentage;
    private UUID legalEntity;
}
