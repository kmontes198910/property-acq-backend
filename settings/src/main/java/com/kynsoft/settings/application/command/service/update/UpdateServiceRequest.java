package com.kynsoft.settings.application.command.service.update;

import com.kynsoft.settings.domain.enums.EServiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateServiceRequest {
    private UUID type;
    private String image;
    private String name;
    private String description;
    private boolean applyIva;
    private EServiceStatus status;
    private Integer estimatedDuration;
}