package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.EvolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Repositorio para operaciones de escritura de evoluciones médicas.
 */
@Repository
public interface EvolutionWriteRepository extends JpaRepository<EvolutionEntity, UUID> {
    
    /**
     * Elimina evoluciones asociadas a una cirugía
     * @param surgeryId ID de la cirugía
     * @return Número de filas afectadas
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM EvolutionEntity e WHERE e.surgeryId = :surgeryId")
    int deleteBySurgeryId(@Param("surgeryId") UUID surgeryId);
}