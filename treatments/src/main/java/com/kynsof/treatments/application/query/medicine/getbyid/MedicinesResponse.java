package com.kynsof.treatments.application.query.medicine.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class MedicinesResponse implements IResponse, Serializable {

    private UUID id;
    private String name;
    private String presentation;


    public MedicinesResponse(MedicinesDto medicinesDto) {
        this.id = medicinesDto.getId();
        this.name = medicinesDto.getName();
        this.presentation = medicinesDto.getPresentation();
    }

}
