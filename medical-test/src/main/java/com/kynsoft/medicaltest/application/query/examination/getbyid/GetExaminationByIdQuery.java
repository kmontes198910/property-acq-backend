package com.kynsoft.medicaltest.application.query.examination.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetExaminationByIdQuery implements IQuery {
    private UUID id;
}
