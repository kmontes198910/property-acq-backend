package com.kynsoft.medicaltest.application.query.labtestparameter.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Consulta para obtener un parámetro de examen de laboratorio por su ID
 */
@Getter
@AllArgsConstructor
public class GetLabTestParameterByIdQuery implements IQuery {
    
    private final UUID id;
}
