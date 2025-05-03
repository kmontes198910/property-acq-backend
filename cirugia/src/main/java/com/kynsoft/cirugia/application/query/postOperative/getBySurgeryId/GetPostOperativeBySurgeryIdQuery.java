package com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostOperativeBySurgeryIdQuery implements IQuery {
    private UUID surgeryId;
}