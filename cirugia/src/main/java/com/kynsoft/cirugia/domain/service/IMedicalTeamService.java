package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.MedicalTeam;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicalTeamService {
    UUID createMedicalTeam(MedicalTeam medicalTeam);
    
    void deleteMedicalTeam(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    
    /**
     * Obtiene todos los integrantes del equipo médico para una cirugía específica.
     * 
     * @param surgeryId Identificador único de la cirugía
     * @return Lista de integrantes del equipo médico para la cirugía especificada
     */
    List<MedicalTeam> findBySurgeryId(UUID surgeryId);
    
    /**
     * Verifica si un miembro específico forma parte del equipo médico de una cirugía determinada.
     * 
     * @param surgeryId Identificador único de la cirugía
     * @param memberId Identificador único del miembro del equipo médico
     * @return true si el miembro forma parte del equipo médico de la cirugía, false en caso contrario
     */
    boolean isMemberInSurgeryTeam(UUID surgeryId, UUID memberId);
}