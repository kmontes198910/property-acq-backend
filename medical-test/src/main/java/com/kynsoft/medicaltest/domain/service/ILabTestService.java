package com.kynsoft.medicaltest.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz de servicio para la gestión de exámenes de laboratorio
 */
public interface ILabTestService {
    
    /**
     * Busca un examen de laboratorio por su ID
     * 
     * @param id El ID del examen de laboratorio
     * @return Un Optional con el DTO del examen si existe
     */
    Optional<LabTestDto> findById(UUID id);
    
    /**
     * Busca un examen de laboratorio por su código
     * 
     * @param code El código del examen de laboratorio
     * @return Un Optional con el DTO del examen si existe
     */
    Optional<LabTestDto> findByCode(String code);
    
    /**
     * Busca exámenes de laboratorio por categoría
     * 
     * @param category La categoría de exámenes a buscar
     * @return Lista de DTOs de exámenes de la categoría especificada
     */
    List<LabTestDto> findByCategory(String category);
    
    /**
     * Crea un nuevo examen de laboratorio
     * 
     * @param labTest DTO con los datos del examen a crear
     * @return El DTO del examen creado
     */
    LabTestDto create(LabTestDto labTest);
    
    /**
     * Actualiza un examen de laboratorio existente
     * 
     * @param labTest DTO con los datos actualizados del examen
     * @return El DTO del examen actualizado
     */
    LabTestDto update(LabTestDto labTest);
    
    /**
     * Elimina un examen de laboratorio por su ID
     * 
     * @param id El ID del examen a eliminar
     */
    void delete(UUID id);
    
    /**
     * Busca exámenes de laboratorio con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Criterios de filtrado
     * @return Respuesta paginada con los exámenes encontrados
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
