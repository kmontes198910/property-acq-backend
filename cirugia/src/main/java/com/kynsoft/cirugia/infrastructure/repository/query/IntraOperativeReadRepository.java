package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.IntraOperativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IntraOperativeReadRepository extends JpaRepository<IntraOperativeEntity, UUID> {
    Optional<IntraOperativeEntity> findById(UUID id);
    List<IntraOperativeEntity> findBySurgeryId(UUID surgeryId);
}
