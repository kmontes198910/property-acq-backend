package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestItemRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface LabTestItemRequestRedRepository extends JpaRepository<LabTestItemRequestEntity, UUID> {
    Page<LabTestItemRequestEntity> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"order", "order.patient"})
    @Override
    Optional<LabTestItemRequestEntity> findById(UUID id);
}
