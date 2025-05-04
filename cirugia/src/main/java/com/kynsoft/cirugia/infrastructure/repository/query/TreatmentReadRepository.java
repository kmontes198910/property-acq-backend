package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para operaciones de lectura de tratamientos médicos.
 */
@Repository
public interface TreatmentReadRepository extends JpaRepository<TreatmentEntity, UUID>, JpaSpecificationExecutor<TreatmentEntity> {
    
    /**
     * Busca tratamientos por ID de cirugía
     * @param surgeryId ID de la cirugía
     * @return Lista de tratamientos asociados a la cirugía
     */
    List<TreatmentEntity> findBySurgeryId(UUID surgeryId);
    
    /**
     * Busca tratamientos por estado
     * @param status Estado del tratamiento
     * @return Lista de tratamientos con el estado especificado
     */
    List<TreatmentEntity> findByStatus(String status);
    
    /**
     * Busca tratamientos por código
     * @param code Código del tratamiento
     * @return Lista de tratamientos con el código especificado
     */
    List<TreatmentEntity> findByCode(String code);
    
    /**
     * Busca tratamientos por nombre (coincidencia parcial)
     * @param name Nombre a buscar
     * @return Lista de tratamientos cuyo nombre contiene el texto especificado
     */
    @Query("SELECT t FROM TreatmentEntity t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<TreatmentEntity> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Busca tratamientos por ID de cirugía y estado
     * @param surgeryId ID de la cirugía
     * @param status Estado del tratamiento
     * @return Lista de tratamientos para la cirugía y estado especificados
     */
    List<TreatmentEntity> findBySurgeryIdAndStatus(UUID surgeryId, String status);
}