package com.kynsoft.cirugia.application.query.medicalteam.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Respuesta que contiene los datos del miembro del equipo médico.
 */
@Getter
@AllArgsConstructor
public class MedicalTeamResponse implements IResponse, Serializable {
    
    private final UUID id;
    private final UUID surgeryId;
    private final UUID memberId;
    private final String memberName;
    private final String memberLastName;
    private final String specialtyName;
    private final String specialtyCode;
    private final String specialityType;
    private final String role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
