package com.kynsof.treatments.application.query.optometryExam.getByPatientId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindByPatientIdOptometryExamQuery implements IQuery {

    private final UUID patientId;

    public FindByPatientIdOptometryExamQuery(UUID id) {
        this.patientId = id;
    }

}
