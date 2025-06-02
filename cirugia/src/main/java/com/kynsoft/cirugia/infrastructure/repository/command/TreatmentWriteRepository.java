package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repositorio para operaciones de escritura de tratamientos médicos.
 */
@Repository
public interface TreatmentWriteRepository extends JpaRepository<TreatmentEntity, UUID> {
    
    /**
     * Actualiza el estado de un tratamiento
     * @param id ID del tratamiento
     * @param status Nuevo estado
     * @param updatedAt Fecha y hora de actualización
     * @param updatedBy ID del usuario que realiza la actualización
     * @return Número de filas afectadas
     */
    @Modifying
    @Transactional
    @Query("UPDATE TreatmentEntity t SET t.status = :status, t.updatedAt = :updatedAt, t.updatedBy = :updatedBy WHERE t.id = :id")
    int updateStatus(@Param("id") UUID id, @Param("status") String status, 
                     @Param("updatedAt") LocalDateTime updatedAt, @Param("updatedBy") UUID updatedBy);
    
    /**
     * Actualiza la cantidad de un tratamiento
     * @param id ID del tratamiento
     * @param quantity Nueva cantidad
     * @param updatedAt Fecha y hora de actualización
     * @param updatedBy ID del usuario que realiza la actualización
     * @return Número de filas afectadas
     */
    @Modifying
    @Transactional
    @Query("UPDATE TreatmentEntity t SET t.quantity = :quantity, t.updatedAt = :updatedAt, t.updatedBy = :updatedBy WHERE t.id = :id")
    int updateQuantity(@Param("id") UUID id, @Param("quantity") int quantity, 
                       @Param("updatedAt") LocalDateTime updatedAt, @Param("updatedBy") UUID updatedBy);
    
    /**
     * Elimina tratamientos asociados a una cirugía
     * @param surgeryId ID de la cirugía
     * @return Número de filas afectadas
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM TreatmentEntity t WHERE t.surgeryId = :surgeryId")
    int deleteBySurgeryId(@Param("surgeryId") UUID surgeryId);
}