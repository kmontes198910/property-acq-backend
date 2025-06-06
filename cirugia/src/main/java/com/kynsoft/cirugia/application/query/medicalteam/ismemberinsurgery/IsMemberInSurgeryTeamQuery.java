package com.kynsoft.cirugia.application.query.medicalteam.ismemberinsurgery;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Consulta para verificar si un miembro específico forma parte del equipo médico de una cirugía.
 */
@Getter
@RequiredArgsConstructor
public class IsMemberInSurgeryTeamQuery implements IQuery {
    
    private final UUID surgeryId;
    private final UUID memberId;
}
