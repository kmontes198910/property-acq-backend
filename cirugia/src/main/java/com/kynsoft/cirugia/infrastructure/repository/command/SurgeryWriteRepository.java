package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SurgeryWriteRepository extends JpaRepository<SurgeryEntity, UUID> {
}