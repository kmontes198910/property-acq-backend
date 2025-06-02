package com.kynsoft.medicaltest.application.query.labtest.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Consulta para obtener un examen de laboratorio por ID
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetLabTestByIdQuery implements IQuery {
    private UUID id;
}
