package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.Evolution;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz que define las operaciones permitidas para el repositorio de evoluciones médicas.
 */
public interface IEvolutionRepository {
    
    /**
     * Crea una nueva evolución médica
     * @param evolution La evolución a crear
     * @return La evolución creada
     */
    Evolution create(Evolution evolution);
    
    /**
     * Actualiza una evolución médica existente.
     * El objeto evolution debe contener todos los campos que se desean mantener.
     * Es responsabilidad del cliente (handler) determinar qué campos deben conservarse
     * de la versión anterior cuando los nuevos son null.
     * 
     * @param evolution La evolución con los datos actualizados
     * @return La evolución actualizada
     */
    Evolution update(Evolution evolution);
    
    /**
     * Busca una evolución por su ID
     * @param id ID de la evolución
     * @return Optional con la evolución si existe
     */
    Optional<Evolution> findById(String id);
    
    /**
     * Busca evoluciones por ID de cirugía
     * @param surgeryId ID de la cirugía
     * @return Lista de evoluciones asociadas a la cirugía
     */
    List<Evolution> findBySurgeryId(UUID surgeryId);
    
    /**
     * Busca evoluciones por ID de cirugía ordenadas por fecha descendente
     * @param surgeryId ID de la cirugía
     * @return Lista de evoluciones ordenadas por fecha (más recientes primero)
     */
    List<Evolution> findBySurgeryIdOrderByDateDesc(UUID surgeryId);
    
    /**
     * Busca evoluciones en un rango de fechas
     * @param startDate Fecha inicial
     * @param endDate Fecha final
     * @return Lista de evoluciones en el rango de fechas especificado
     */
    List<Evolution> findByEvolutionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Elimina una evolución por su ID
     * @param id ID de la evolución a eliminar
     */
    void deleteById(String id);
    
    /**
     * Realiza una búsqueda paginada con filtros personalizados
     * @param pageable Configuración de paginación
     * @param filterCriteria Criterios de filtrado
     * @return Respuesta paginada con las evoluciones que cumplen los criterios
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}