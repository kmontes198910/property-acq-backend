package com.kynsof.treatments.infrastructure.repositories.command;

import com.kynsof.treatments.infrastructure.entity.VitalSigns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VitalSignsWriteDataJPARepository extends JpaRepository<VitalSigns, UUID> {
}
