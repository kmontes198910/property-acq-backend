package com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Respuesta que contiene una lista de miembros del equipo médico para una cirugía.
 */
@Getter
@AllArgsConstructor
public class MedicalTeamListResponse implements IResponse, Serializable {
    
    private final UUID surgeryId;
    private final List<MedicalTeamResponse> members;
}
