package com.kynsof.hospitalizationService.domain.service;


import com.kynsof.hospitalizationService.domain.dto.RecoveryRoom;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IRecoveryRoomService {

    /**
     * Busca una sala de recuperación por su ID
     * @param id El ID de la sala de recuperación
     * @return Optional que contiene la sala de recuperación si existe
     */
    RecoveryRoom findById(UUID id);

    /**
     * Obtiene todas las salas de recuperación para un negocio específico
     * @param businessId El ID del negocio
     * @return Lista de salas de recuperación
     */
    List<RecoveryRoom> findByBusinessId(UUID businessId);

    /**
     * Obtiene salas de recuperación por tipo
     * @param type El tipo de sala
     * @return Lista de salas de recuperación del tipo especificado
     */
    List<RecoveryRoom> findByType(String type);

    /**
     * Obtiene salas de recuperación por estado
     * @param status El estado de la sala
     * @return Lista de salas de recuperación con el estado especificado
     */
    List<RecoveryRoom> findByStatus(String status);

    /**
     * Crea una nueva sala de recuperación
     * @param recoveryRoom La sala de recuperación a crear
     * @return La sala de recuperación creada con su ID generado
     */
    RecoveryRoom create(RecoveryRoom recoveryRoom);

    /**
     * Actualiza una sala de recuperación existente
     * @param recoveryRoom La sala de recuperación con los datos actualizados
     * @return La sala de recuperación actualizada
     */
    RecoveryRoom update(RecoveryRoom recoveryRoom);

    /**
     * Actualiza el estado de una sala de recuperación
     * @param roomId El ID de la sala de recuperación
     * @param status El nuevo estado
     */
    void updateStatus(UUID roomId, String status);

    /**
     * Elimina una sala de recuperación
     * @param id El ID de la sala de recuperación a eliminar
     */
    void deleteById(UUID id);

    /**
     * Busca salas de recuperación con filtros y paginación
     * @param pageable Información de paginación
     * @param filterCriteria Criterios de filtrado
     * @return Respuesta paginada con las salas que coinciden con los criterios
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}