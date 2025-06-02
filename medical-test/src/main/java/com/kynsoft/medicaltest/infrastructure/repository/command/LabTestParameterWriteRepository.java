package com.kynsoft.medicaltest.infrastructure.repository.command;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LabTestParameterWriteRepository extends JpaRepository<LabTestParameterEntity, UUID> {
}