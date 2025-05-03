package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.IntraOperativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IntraOperativeWriteRepository extends JpaRepository<IntraOperativeEntity, UUID> {
    // Métodos de escritura personalizados si los necesitas
}
