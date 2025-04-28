package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.OwnerDto;
import com.kynsoft.propertyacqcenter.domain.enums.OwnerType;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    private String names;

    @Enumerated(EnumType.STRING)
    private OwnerType type;

    private String ownerStatus;

    public Owner(OwnerDto ownerDto) {
        this.names = ownerDto.getNames();
        this.type = ownerDto.getType();
        this.ownerStatus = ownerDto.getStatus();
    }

    public OwnerDto toAggregate() {
        return OwnerDto.builder()
                .names(names)
                .type(type)
                .status(ownerStatus)
                .build();
    }
}