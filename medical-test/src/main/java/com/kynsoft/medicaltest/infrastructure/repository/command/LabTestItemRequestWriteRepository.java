package com.kynsoft.medicaltest.infrastructure.repository.command;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestItemRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LabTestItemRequestWriteRepository extends JpaRepository<LabTestItemRequestEntity, UUID> {
}