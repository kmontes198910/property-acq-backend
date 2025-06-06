package com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Consulta para obtener todos los miembros del equipo médico para una cirugía específica.
 */
@Getter
@RequiredArgsConstructor
public class GetMedicalTeamBySurgeryIdQuery implements IQuery {
    
    private final UUID surgeryId;
}
