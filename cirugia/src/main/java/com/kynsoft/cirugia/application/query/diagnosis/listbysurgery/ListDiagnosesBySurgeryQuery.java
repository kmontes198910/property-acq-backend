package com.kynsoft.cirugia.application.query.diagnosis.listbysurgery;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListDiagnosesBySurgeryQuery implements IQuery {
    private final UUID surgeryId;

    public ListDiagnosesBySurgeryQuery(UUID surgeryId) {
        this.surgeryId = surgeryId;
    }
}