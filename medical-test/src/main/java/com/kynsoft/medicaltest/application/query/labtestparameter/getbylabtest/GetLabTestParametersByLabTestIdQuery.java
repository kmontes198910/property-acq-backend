package com.kynsoft.medicaltest.application.query.labtestparameter.getbylabtest;


import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * Consulta para obtener todos los parámetros de un examen de laboratorio
 */
@Getter
@AllArgsConstructor
public class GetLabTestParametersByLabTestIdQuery implements IQuery {
    
    private final UUID labTestId;
}
