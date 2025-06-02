package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.BedAssignmentEntity;
import com.kynsoft.cirugia.infrastructure.entities.PreOperativeEntity;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PreOperativeReadRepository extends JpaRepository<PreOperativeEntity, UUID>, JpaSpecificationExecutor<BedAssignmentEntity> {


    Optional<PreOperativeEntity> findBySurgeryId(UUID surgeryId);
}
