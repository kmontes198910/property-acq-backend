package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.AnesthesiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnesthesiaReadRepository extends JpaRepository<AnesthesiaEntity, UUID>, JpaSpecificationExecutor<AnesthesiaEntity> {
    
    /**
     * Busca registro de anestesia por ID de cirugía
     * @param surgeryId ID de la cirugía
     * @return Optional con el registro de anestesia si existe
     */
    Optional<AnesthesiaEntity> findBySurgeryId(UUID surgeryId);
}
