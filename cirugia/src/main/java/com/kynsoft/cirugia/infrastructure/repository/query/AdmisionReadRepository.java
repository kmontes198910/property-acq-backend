package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.AdmisionEntity;
import com.kynsoft.cirugia.infrastructure.entities.AnesthesiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdmisionReadRepository extends JpaRepository<AdmisionEntity, UUID>, JpaSpecificationExecutor<AdmisionEntity> {
    
    /**
     * Busca registro de anestesia por ID de cirugía
     * @param surgeryId ID de la cirugía
     * @return Optional con el registro de anestesia si existe
     */
    Optional<AdmisionEntity> findBySurgeryId(UUID surgeryId);
}
