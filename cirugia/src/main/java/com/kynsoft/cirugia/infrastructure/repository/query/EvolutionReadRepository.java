package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.EvolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositorio para operaciones de lectura de evoluciones médicas.
 */
@Repository
public interface EvolutionReadRepository extends JpaRepository<EvolutionEntity, UUID>, JpaSpecificationExecutor<EvolutionEntity> {
    
    /**
     * Busca evoluciones por ID de cirugía
     * @param surgeryId ID de la cirugía
     * @return Lista de evoluciones asociadas a la cirugía
     */
    List<EvolutionEntity> findBySurgeryId(UUID surgeryId);
    
    /**
     * Busca evoluciones por ID de cirugía ordenadas por fecha de evolución (más reciente primero)
     * @param surgeryId ID de la cirugía
     * @return Lista de evoluciones ordenadas por fecha descendente
     */
    @Query("SELECT e FROM EvolutionEntity e WHERE e.surgeryId = :surgeryId ORDER BY e.evolutionDate DESC")
    List<EvolutionEntity> findBySurgeryIdOrderByEvolutionDateDesc(@Param("surgeryId") UUID surgeryId);
    
    /**
     * Busca evoluciones en un rango de fechas
     * @param startDate Fecha inicial
     * @param endDate Fecha final
     * @return Lista de evoluciones registradas en el rango de fechas
     */
    @Query("SELECT e FROM EvolutionEntity e WHERE e.evolutionDate BETWEEN :startDate AND :endDate")
    List<EvolutionEntity> findByEvolutionDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}