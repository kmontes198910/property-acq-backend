package com.kynsoft.medicaltest.infrastructure.repository.command;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LabTestResultWriteRepository extends JpaRepository<LabTestResultEntity, UUID> {
}