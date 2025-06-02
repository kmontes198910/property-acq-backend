package com.kynsoft.cirugia.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.infrastructure.entities.SpecialtyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyResponse implements IResponse, Serializable {
    private UUID id;
    private String name;

    
    public SpecialtyResponse(SpecialtyEntity specialty) {
        if (specialty != null) {
            this.id = specialty.getId();
            this.name = specialty.getName();
        }
    }
}