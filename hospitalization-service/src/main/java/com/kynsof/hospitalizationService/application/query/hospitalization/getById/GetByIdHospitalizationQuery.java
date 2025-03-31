package com.kynsof.hospitalizationService.application.query.hospitalization.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdHospitalizationQuery implements IQuery {
    private UUID id;
}
