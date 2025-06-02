package com.kynsoft.medicaltest.infrastructure.repository.command;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LabTestRequestWriteRepository extends JpaRepository<LabTestRequestEntity, UUID> {
}