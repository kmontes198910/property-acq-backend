package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.BedAssignment;
import java.util.List;
import java.util.UUID;

/**
 * Interfaz para operaciones de repositorio relacionadas con asignaciones de camas
 */
public interface IBedAssignmentRepository {
    
    /**
     * Crea una nueva asignación de cama y gestiona asignaciones previas
     * @param bedAssignment Datos de la asignación de cama
     * @return Asignación de cama creada
     */
    BedAssignment createAssignment(BedAssignment bedAssignment);
    
    /**
     * Busca todas las asignaciones de cama por ID de cirugía
     * @param surgeryId ID de la cirugía
     * @return Lista de asignaciones de cama para la cirugía especificada
     */
    List<BedAssignment> findBySurgeryId(UUID surgeryId);
}
