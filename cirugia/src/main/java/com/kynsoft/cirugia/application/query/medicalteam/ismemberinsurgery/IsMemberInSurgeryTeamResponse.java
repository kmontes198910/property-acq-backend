package com.kynsoft.cirugia.application.query.medicalteam.ismemberinsurgery;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Respuesta que indica si un miembro específico forma parte del equipo médico de una cirugía.
 */
@Getter
@AllArgsConstructor
public class IsMemberInSurgeryTeamResponse implements IResponse, Serializable {
    
    private final boolean isMemberInTeam;
}
