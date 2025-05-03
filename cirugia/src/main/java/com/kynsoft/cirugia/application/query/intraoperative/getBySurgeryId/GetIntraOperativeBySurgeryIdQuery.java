package com.kynsoft.cirugia.application.query.intraoperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GetIntraOperativeBySurgeryIdQuery implements IQuery {
    private UUID surgeryId;
}