package com.kynsoft.medicaltest.application.query.doctor.getbyid;

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
public class GetDoctorByIdQuery implements IQuery {
    private UUID id;
}
